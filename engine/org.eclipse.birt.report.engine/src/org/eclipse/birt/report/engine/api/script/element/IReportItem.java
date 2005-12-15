package org.eclipse.birt.report.engine.api.script.element;

import org.eclipse.birt.report.engine.api.script.ScriptException;

/**
 * Represents a the design of a report item in the scripting environment
 */
public interface IReportItem extends IReportElement
{

	/**
	 * Gets a handle to deal with the item's x (horizontal) position.
	 * 
	 * @return The item's x position.
	 */

	String getX( );

	/**
	 * Gets a handle to deal with the item's y (vertical) position.
	 * 
	 * @return The item's y position.
	 */

	String getY( );

	/**
	 * Sets the item's x position using a dimension string with optional unit
	 * suffix such as "10" or "10pt". If no suffix is provided, then the units
	 * are assumed to be in the design's default units. Call this method to set
	 * a string typed in by the user.
	 * 
	 * @param dimension
	 *            dimension string with optional unit suffix.
	 * @throws ScriptException
	 *             if the string is not valid
	 */

	void setX( String dimension ) throws ScriptException;

	/**
	 * Sets the item's x position to a value in default units. The default unit
	 * may be defined by the property in BIRT or the application unit defined in
	 * the design session.
	 * 
	 * @param dimension
	 *            the new value in application units.
	 * @throws ScriptException
	 *             if the property is locked.
	 */

	void setX( double dimension ) throws ScriptException;

	/**
	 * Sets the item's y position using a dimension string with optional unit
	 * suffix such as "10" or "10pt". If no suffix is provided, then the units
	 * are assumed to be in the design's default units. Call this method to set
	 * a string typed in by the user.
	 * 
	 * @param dimension
	 *            dimension string with optional unit suffix.
	 * @throws ScriptException
	 *             if the string is not valid
	 */

	void setY( String dimension ) throws ScriptException;

	/**
	 * Sets the item's y position to a value in default units. The default unit
	 * may be defined by the property in BIRT or the application unit defined in
	 * the design session.
	 * 
	 * @param dimension
	 *            the new value in application units.
	 * @throws ScriptException
	 *             if the property is locked.
	 */

	void setY( double dimension ) throws ScriptException;

	/**
	 * Sets the item's height using a dimension string with optional unit suffix
	 * such as "10" or "10pt". If no suffix is provided, then the units are
	 * assumed to be in the design's default units. Call this method to set a
	 * string typed in by the user.
	 * 
	 * @param dimension
	 *            dimension string with optional unit suffix.
	 * @throws ScriptException
	 *             if the string is not valid
	 */

	void setHeight( String dimension ) throws ScriptException;

	/**
	 * Sets the item's height to a value in default units. The default unit may
	 * be defined by the property in BIRT or the application unit defined in the
	 * design session.
	 * 
	 * @param dimension
	 *            the new value in application units.
	 * @throws ScriptException
	 *             if the property is locked.
	 */

	void setHeight( double dimension ) throws ScriptException;

	/**
	 * Sets the item's width using a dimension string with optional unit suffix
	 * such as "10" or "10pt". If no suffix is provided, then the units are
	 * assumed to be in the design's default units. Call this method to set a
	 * string typed in by the user.
	 * 
	 * @param dimension
	 *            dimension string with optional unit suffix.
	 * @throws ScriptException
	 *             if the string is not valid
	 */

	void setWidth( String dimension ) throws ScriptException;

	/**
	 * Sets the item's width to a value in default units. The default unit may
	 * be defined by the property in BIRT or the application unit defined in the
	 * design session.
	 * 
	 * @param dimension
	 *            the new value in application units.
	 * @throws ScriptException
	 *             if the property is locked.
	 */

	void setWidth( double dimension ) throws ScriptException;

	/**
	 * Gets a handle to deal with the item's width.
	 * 
	 * @return a DimensionHandle for the item's width.
	 */

	String getWidth( );

	/**
	 * Gets a handle to deal with the item's height.
	 * 
	 * @return a DimensionHandle for the item's height.
	 */
	String getHeight( );

	/**
	 * Returns the bookmark of the report item.
	 * 
	 * @return the book mark as a string
	 */

	String getBookmark( );

	/**
	 * Sets the bookmark of the report item.
	 * 
	 * @param value
	 *            the property value to be set.
	 * @throws ScriptException
	 *             if the property is locked.
	 */

	void setBookmark( String value ) throws ScriptException;

	/**
	 * Sets a table of contents entry for this item. The TOC property defines an
	 * expression that returns a string that is to appear in the Table of
	 * Contents for this item or its container.
	 * 
	 * @param expression
	 *            the expression that returns a string
	 * @throws ScriptException
	 *             if the TOC property is locked by the property mask.
	 * 
	 * @see #getTocExpression()
	 */

	void setTocExpression( String expression ) throws ScriptException;

	/**
	 * Returns the expression evalueated as a table of contents entry for this
	 * item.
	 * 
	 * @return the expression evaluated as a table of contents entry for this
	 *         item
	 * @see #setTocExpression(String)
	 */

	String getTocExpression( );

}