
/*******************************************************************************
 * Copyright (c) 2004, 2007 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/
package org.eclipse.birt.data.engine.binding;

import org.eclipse.birt.core.data.DataType;
import org.eclipse.birt.data.engine.api.APITestCase;
import org.eclipse.birt.data.engine.api.IBinding;
import org.eclipse.birt.data.engine.api.IPreparedQuery;
import org.eclipse.birt.data.engine.api.IQueryResults;
import org.eclipse.birt.data.engine.api.IResultIterator;
import org.eclipse.birt.data.engine.api.IResultMetaData;
import org.eclipse.birt.data.engine.api.querydefn.Binding;
import org.eclipse.birt.data.engine.api.querydefn.FilterDefinition;
import org.eclipse.birt.data.engine.api.querydefn.QueryDefinition;
import org.eclipse.birt.data.engine.api.querydefn.ScriptExpression;

import testutil.ConfigText;
/**
 * 
 */

public class QueryCacheTest extends APITestCase
{
	protected DataSourceInfo getDataSourceInfo( )
	{
		return new DataSourceInfo( ConfigText.getString( "Binding.TestData.TableName" ),
				ConfigText.getString( "Binding.TestData.TableSQL" ),
				ConfigText.getString( "Binding.TestData.TestDataFileName" ) );
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void testBasicCache( ) throws Exception
	{
		QueryDefinition query = new QueryDefinition();
		query.setDataSetName( this.dataSet.getName( ) );
		query.setAutoBinding( true );
		query.setCacheQueryResults( true );
		IQueryResults queryResults = this.dataEngine.prepare( query ).execute( null );
		String id = queryResults.getID( );
		IResultIterator it = queryResults.getResultIterator( );
		it.close( );
		QueryDefinition newQuery = new QueryDefinition();
		newQuery.setQueryResultsID( id );
		IPreparedQuery pq = this.dataEngine.prepare( newQuery );
		IQueryResults result = pq.execute( null );
		it = result.getResultIterator( );
		this.outputQueryResult( it, new String[]{"CITY", "AMOUNT"} );
		this.checkOutputFile( );
		
	}
	
	public void testBasicCache1( ) throws Exception
	{
		QueryDefinition query = new QueryDefinition();
		query.setDataSetName( this.dataSet.getName( ) );
		query.setAutoBinding( true );
		query.setCacheQueryResults( true );
		IQueryResults queryResults = this.dataEngine.prepare( query ).execute( null );
		String id = queryResults.getID( );
		IResultIterator it = queryResults.getResultIterator( );
		it.close( );
		
		QueryDefinition newQuery = new QueryDefinition();
		newQuery.setQueryResultsID( id );
		IPreparedQuery pq = this.dataEngine.prepare( newQuery );
		IQueryResults result = pq.execute( null );
		it = result.getResultIterator( );
		assertEquals( it.getValue( "CITY" ), "Beijing" );
		assertEquals( it.getValue( "AMOUNT" ), 7000 );

		it.next( );
		assertEquals( it.getValue( "CITY" ), "Beijing" );
		assertEquals( it.getValue( "AMOUNT" ), 7000 );

		it.next( );
		assertEquals( it.getValue( "CITY" ), "New York" );
		assertEquals( it.getValue( "AMOUNT" ), 100 );	
	}
	
	public void testCacheEmptyResultSet( ) throws Exception
	{
		QueryDefinition query = new QueryDefinition();
		query.setDataSetName( this.dataSet.getName( ) );
		query.setAutoBinding( true );
		query.setCacheQueryResults( true );
		query.addFilter( new FilterDefinition( new ScriptExpression("false")) );
		IQueryResults queryResults = this.dataEngine.prepare( query ).execute( null );
		queryResults.getResultIterator( ).close( );
	}
	
	public void testCacheEmptyResultSet1( ) throws Exception
	{
		QueryDefinition query = new QueryDefinition();
		query.setDataSetName( this.dataSet.getName( ) );
		query.setAutoBinding( true );
		query.setCacheQueryResults( true );
		query.addFilter( new FilterDefinition( new ScriptExpression("false")) );
		IQueryResults queryResults = this.dataEngine.prepare( query ).execute( null );
		String id = queryResults.getID( );
		IResultIterator it = queryResults.getResultIterator( );
		it.close( );
		
		QueryDefinition newQuery = new QueryDefinition();
		newQuery.setQueryResultsID( id );
		IPreparedQuery pq = this.dataEngine.prepare( newQuery );
		IQueryResults result = pq.execute( null );
		it = result.getResultIterator( );
		assertEquals( it.getValue( "CITY" ), null );
		assertEquals( it.getValue( "AMOUNT" ), null );
		assertEquals( it.next( ), false );
	}
	
	public void testUseDetailsCache( ) throws Exception
	{
		QueryDefinition query = new QueryDefinition();
		query.setDataSetName( this.dataSet.getName( ) );
		query.setAutoBinding( true );
		query.setCacheQueryResults( true );
		query.setUsesDetails( false );
		IQueryResults queryResults = this.dataEngine.prepare( query ).execute( null );
		String id = queryResults.getID( );
		IResultIterator it = queryResults.getResultIterator( );
		it.close( );
		QueryDefinition newQuery = new QueryDefinition();
		newQuery.setQueryResultsID( id );
		IPreparedQuery pq = this.dataEngine.prepare( newQuery );
		IQueryResults result = pq.execute( null );
		this.outputQueryResult( result.getResultIterator( ), new String[]{"CITY", "AMOUNT"} );
		this.checkOutputFile( );
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void testSerializableJavaObjectCache( ) throws Exception
	{
		QueryDefinition query = new QueryDefinition();
		query.setDataSetName( this.dataSet.getName( ) );
		ScriptExpression se = new ScriptExpression( "new java.lang.StringBuffer(\"ss\")" );
		se.setDataType( DataType.JAVA_OBJECT_TYPE );
		IBinding b = new Binding("serializable", se );
		b.setDataType( DataType.JAVA_OBJECT_TYPE );
		query.addBinding( b );
		query.setCacheQueryResults( true );
		IQueryResults queryResults = this.dataEngine.prepare( query ).execute( null );
		String id = queryResults.getID( );
		IResultIterator it = queryResults.getResultIterator( );
		it.close( );
		QueryDefinition newQuery = new QueryDefinition();
		newQuery.setQueryResultsID( id );
		IPreparedQuery pq = this.dataEngine.prepare( newQuery );
		IQueryResults result = pq.execute( null );
		it = result.getResultIterator( );
		IResultMetaData meta = it.getResultMetaData( );
		//org.eclipse.birt.data.engine.impl.CacheResultIterator#getResultMetaData only returns 
		//meta data of data set columns
		//assertEquals( DataType.OBJECT_TYPE, meta.getColumnType( 1 ));
		int count = 0;
		while ( it.next( ) )
		{
			Object value = it.getValue( "serializable" );
			assertTrue( value instanceof StringBuffer );
			assertEquals( "ss", value.toString( ) );
			count++;
		}
		assertTrue( count > 0 );
		it.close( );
		
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void testUnserializableJavaObjectCache( ) throws Exception
	{
		QueryDefinition query = new QueryDefinition();
		query.setDataSetName( this.dataSet.getName( ) );
		ScriptExpression se = new ScriptExpression( "new java.lang.ThreadGroup(\"ss\")" );
		se.setDataType( DataType.JAVA_OBJECT_TYPE );
		IBinding b = new Binding("unserializable", se );
		b.setDataType( DataType.JAVA_OBJECT_TYPE );
		query.addBinding( b );
		query.setCacheQueryResults( true );
		IQueryResults queryResults = this.dataEngine.prepare( query ).execute( null );
		String id = queryResults.getID( );
		IResultIterator it = null;
		try 
		{
			it = queryResults.getResultIterator( );
		}
		catch ( Exception e )
		{
			//Currently, writing unserializable objects cause exception
			e.printStackTrace( );
			return;
		}
		assertTrue( false );
		it.close( );
		QueryDefinition newQuery = new QueryDefinition();
		newQuery.setQueryResultsID( id );
		IPreparedQuery pq = this.dataEngine.prepare( newQuery );
		IQueryResults result = pq.execute( null );
		it = result.getResultIterator( );
		IResultMetaData meta = it.getResultMetaData( );
		//org.eclipse.birt.data.engine.impl.CacheResultIterator#getResultMetaData only returns 
		//meta data of data set columns
		//assertEquals( DataType.OBJECT_TYPE, meta.getColumnType( 1 ));
		int count = 0;
		while ( it.next( ) )
		{
			Object value = it.getValue( "unserializable" );
			count++;
		}
		assertTrue( count > 0 );
		it.close( );
		
	}
}
