/*******************************************************************************
 * License
 * 
 * +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++
 * 
 * Lecture2Go 
 * 
 * The Lecture2Go software is based on the liferay portal 6.2-ga6
 * <http://www.liferay.com> (Copyright notice see below)
 * Lecture2Go <http://lecture2go.uni-hamburg.de> is an open source
 * platform for media management and distribution. Our goal is to
 * support the free access to knowledge because this is a component
 * of each democratic society. The open source software is aimed at
 * academic institutions and has to strengthen the blended learning.
 * All Lecture2Go plugins are continuously being developed and improved.
 * For more details please visit <http://lecture2go-open-source.rrz.uni-hamburg.de>
 * Copyright (c) 2013 - present University of Hamburg / Computer and Data Center (RRZ)
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *  
 * +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++
 * 
 * The Liferay Plugins SDK:
 * 
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++
 * 
 * Third Party Software
 * 
 * Lecture2Go uses third-party libraries which may be distributed under different licenses
 * to the above (but are compatible with the used GPL license). Informations about these
 * licenses and copyright informations are mostly detailed in the library source code or jars themselves.
 * You must agree to the terms of these licenses, in addition tothe above Lecture2Go source code license,
 * in order to use this software.
 ******************************************************************************/
package com.josephoconnell.html;
/**
 * 
 * HTML filtering utility for protecting against XSS (Cross Site Scripting).
 *
 * This code is licensed under a Creative Commons Attribution-ShareAlike 2.5 License
 * http://creativecommons.org/licenses/by-sa/2.5/
 * 
 * This code is a Java port of the original work in PHP by Cal Hendersen.
 * http://code.iamcal.com/php/lib_filter/
 *
 * The trickiest part of the translation was handling the differences in regex handling
 * between PHP and Java.  These resources were helpful in the process:
 * 
 * http://java.sun.com/j2se/1.4.2/docs/api/java/util/regex/Pattern.html
 * http://us2.php.net/manual/en/reference.pcre.pattern.modifiers.php
 * http://www.regular-expressions.info/modifiers.html
 * 
 * A note on naming conventions: instance variables are prefixed with a "v"; global
 * constants are in all caps.
 * 
 * Sample use:
 * String input = ...
 * String clean = new HTMLInputFilter().filter( input );
 * 
 * If you find bugs or have suggestions on improvement (especially regarding 
 * perfomance), please contact me at the email below.  The latest version of this
 * source can be found at
 * 
 * http://josephoconnell.com/java/xss-html-filter/
 *
 * @author Joseph O'Connell <joe.oconnell at gmail dot com>
 * @version 1.0 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;
import junit.framework.TestCase;


/**
 * The Class HTMLInputFilter.
 */
public class HTMLInputFilter
{	
	
	/** The Constant ALWAYS_MAKE_TAGS. */
	protected static final boolean ALWAYS_MAKE_TAGS = true;
	
	/** The Constant STRIP_COMMENTS. */
	protected static final boolean STRIP_COMMENTS = true;
	
	/** The Constant REGEX_FLAGS_SI. */
	protected static final int REGEX_FLAGS_SI = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
	
	/** The v allowed. */
	protected Map<String,List<String>> vAllowed;
	
	/** The v tag counts. */
	protected Map<String,Integer> vTagCounts;
	
	/** The v self closing tags. */
	protected String[] vSelfClosingTags;
	
	/** The v need closing tags. */
	protected String[] vNeedClosingTags;
	
	/** The v protocol atts. */
	protected String[] vProtocolAtts;
	
	/** The v allowed protocols. */
	protected String[] vAllowedProtocols;
	
	/** The v remove blanks. */
	protected String[] vRemoveBlanks;
	
	/** The v allowed entities. */
	protected String[] vAllowedEntities;
	
	/** The v debug. */
	protected boolean vDebug;
	
	/**
	 * Instantiates a new hTML input filter.
	 */
	public HTMLInputFilter()
	{
		this(false);
	}
	
	/**
	 * Instantiates a new hTML input filter.
	 *
	 * @param debug the debug
	 */
	public HTMLInputFilter( boolean debug )
	{
		vDebug = debug;
		
		vAllowed = new HashMap<String,List<String>>();
		vTagCounts = new HashMap<String,Integer>();
		
		ArrayList<String> a_atts = new ArrayList<String>();
		a_atts.add( "href" );
		a_atts.add( "target" );
		vAllowed.put( "a", a_atts );
		
		ArrayList<String> img_atts = new ArrayList<String>();
		img_atts.add( "src" );
		img_atts.add( "width" );
		img_atts.add( "height" );
		img_atts.add( "alt" );
		vAllowed.put( "img", img_atts );
		
		ArrayList<String> no_atts = new ArrayList<String>();
		vAllowed.put( "b", no_atts );
		vAllowed.put( "strong", no_atts );
		vAllowed.put( "i", no_atts );
		vAllowed.put( "em", no_atts );
		
		vSelfClosingTags = new String[] { "img" };
		vNeedClosingTags = new String[] { "a", "b", "strong", "i", "em" };
		vAllowedProtocols = new String[] { "http", "mailto" }; // no ftp.
		vProtocolAtts = new String[] { "src", "href" };
		vRemoveBlanks = new String[] { "a", "b", "strong", "i", "em" };
		vAllowedEntities = new String[] { "amp", "gt", "lt", "quot" };
	}
	
	/**
	 * Reset.
	 */
	protected void reset()
	{
		vTagCounts = new HashMap<String,Integer>();
	}
	
	/**
	 * Debug.
	 *
	 * @param msg the msg
	 */
	protected void debug( String msg )
	{
		if (vDebug)
			System.out.println( msg );
	}
	
	//---------------------------------------------------------------
	// my versions of some PHP library functions
	
	/**
	 * Chr.
	 *
	 * @param decimal the decimal
	 * @return the string
	 */
	public static String chr( int decimal )
	{
		return String.valueOf( (char) decimal );
	}
	
	/**
	 * Html special chars.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String htmlSpecialChars( String s )
	{
		s = s.replaceAll( "&", "&amp;" );
		s = s.replaceAll( "\"", "&quot;" );
		s = s.replaceAll( "<", "&lt;" );
		s = s.replaceAll( ">", "&gt;" );
		return s;
	}
	
	//---------------------------------------------------------------
	
	/**
	 * Filter.
	 *
	 * @param input the input
	 * @return the string
	 */
	public synchronized String filter( String input )
	{
		reset();
		String s = input;
		
		debug( "************************************************" );
		debug( "              INPUT: " + input );
		
		s = escapeComments(s);
		debug( "     escapeComments: " + s );
		
		s = balanceHTML(s);
		debug( "        balanceHTML: " + s );
		
		s = checkTags(s);
		debug( "          checkTags: " + s );
		
		s = processRemoveBlanks(s);
		debug( "processRemoveBlanks: " + s );
		
		s = validateEntities(s);
		debug( "    validateEntites: " + s );
		
		debug( "************************************************\n\n" );
		return s;
	}
	
	/**
	 * Escape comments.
	 *
	 * @param s the s
	 * @return the string
	 */
	protected String escapeComments( String s )
	{
		Pattern p = Pattern.compile( "<!--(.*?)-->", Pattern.DOTALL );
		Matcher m = p.matcher( s );
		StringBuffer buf = new StringBuffer();
		if (m.find()) {
			String match = m.group( 1 ); //(.*?)
			m.appendReplacement( buf, "<!--" + htmlSpecialChars( match ) + "-->" );
		}
		m.appendTail( buf );
		
		return buf.toString();
	}
	
	/**
	 * Balance html.
	 *
	 * @param s the s
	 * @return the string
	 */
	protected String balanceHTML( String s )
	{
		if (ALWAYS_MAKE_TAGS) 
		{
			//
			// try and form html
			//
			s = regexReplace("^>", "", s);
			s = regexReplace("<([^>]*?)(?=<|$)", "<$1>", s);
			s = regexReplace("(^|>)([^<]*?)(?=>)", "$1<$2", s);
			
		} 
		else
		{
			//
			// escape stray brackets
			//
			s = regexReplace("<([^>]*?)(?=<|$)", "&lt;$1", s);
			s = regexReplace("(^|>)([^<]*?)(?=>)", "$1$2&gt;<", s);
			
			//
			// the last regexp causes '<>' entities to appear
			// (we need to do a lookahead assertion so that the last bracket can
			// be used in the next pass of the regexp)
			//
			s = s.replaceAll("<>", "");
		}
		
		return s;
	}
	
	/**
	 * Check tags.
	 *
	 * @param s the s
	 * @return the string
	 */
	protected String checkTags( String s )
	{		
		Pattern p = Pattern.compile( "<(.*?)>", Pattern.DOTALL );
		Matcher m = p.matcher( s );
		
		StringBuffer buf = new StringBuffer();
		while (m.find()) {
			String replaceStr = m.group( 1 );
			replaceStr = processTag( replaceStr );
			m.appendReplacement(buf, replaceStr);
		}
		m.appendTail(buf);
		
		s = buf.toString();
		
		// these get tallied in processTag
		// (remember to reset before subsequent calls to filter method)
		for( String key : vTagCounts.keySet())
		{
			for(int ii=0; ii<vTagCounts.get(key); ii++) {
				s += "</" + key + ">";
			}
		}
		
		return s;
	}
	
	/**
	 * Process remove blanks.
	 *
	 * @param s the s
	 * @return the string
	 */
	protected String processRemoveBlanks( String s )
	{
		for( String tag : vRemoveBlanks )
		{
			s = regexReplace( "<" + tag + "(\\s[^>]*)?></" + tag + ">", "", s );
			s = regexReplace( "<" + tag + "(\\s[^>]*)?/>", "", s );
		}
		
		return s;
	}
	
	/**
	 * Regex replace.
	 *
	 * @param regex_pattern the regex_pattern
	 * @param replacement the replacement
	 * @param s the s
	 * @return the string
	 */
	protected String regexReplace( String regex_pattern, String replacement, String s )
	{
		Pattern p = Pattern.compile( regex_pattern );
		Matcher m = p.matcher( s );
		return m.replaceAll( replacement );
	}
	
	/**
	 * Process tag.
	 *
	 * @param s the s
	 * @return the string
	 */
	protected String processTag( String s )
	{		
		// ending tags
		Pattern p = Pattern.compile( "^/([a-z0-9]+)", REGEX_FLAGS_SI );
		Matcher m = p.matcher( s );
		if (m.find()) {
			String name = m.group(1).toLowerCase();
			if (vAllowed.containsKey( name )) {
				if (!inArray(name, vSelfClosingTags)) {
					if (vTagCounts.containsKey( name )) {
						vTagCounts.put(name, vTagCounts.get(name)-1);
						return "</" + name + ">";
					}
				}
			}
		}
		
		// starting tags
		p = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", REGEX_FLAGS_SI);
		m = p.matcher( s );
		if (m.find()) {
			String name = m.group(1).toLowerCase();
			String body = m.group(2);
			String ending = m.group(3);
			
			//debug( "in a starting tag, name='" + name + "'; body='" + body + "'; ending='" + ending + "'" );
			if (vAllowed.containsKey( name )) {
				String params = "";
				
				Pattern p2 = Pattern.compile("([a-z0-9]+)=([\"'])(.*?)\\2", REGEX_FLAGS_SI);
				Pattern p3 = Pattern.compile("([a-z0-9]+)(=)([^\"\\s']+)", REGEX_FLAGS_SI);
				Matcher m2 = p2.matcher( body );
				Matcher m3 = p3.matcher( body );
				List<String> paramNames = new ArrayList<String>();
				List<String> paramValues = new ArrayList<String>();
				while (m2.find()) {
					paramNames.add(m2.group(1)); //([a-z0-9]+)
					paramValues.add(m2.group(3)); //(.*?)
				}
				while (m3.find()) {
					paramNames.add(m3.group(1)); //([a-z0-9]+)
					paramValues.add(m3.group(3)); //([^\"\\s']+)
				}
				
				String paramName, paramValue;
				for( int ii=0; ii<paramNames.size(); ii++ ) {
					paramName = paramNames.get(ii).toLowerCase();
					paramValue = paramValues.get(ii);
					
					//debug( "paramName='" + paramName + "'" );
					//debug( "paramValue='" + paramValue + "'" );
					//debug( "allowed? " + vAllowed.get( name ).contains( paramName ) );
					
					if (vAllowed.get( name ).contains( paramName )) {
						if (inArray( paramName, vProtocolAtts )) {
							paramValue = processParamProtocol( paramValue );
						}
						params += " " + paramName + "=\"" + paramValue + "\"";
					}
				}
				
				if (inArray( name, vSelfClosingTags )) {
					ending = " /";
				}
				
				if (inArray( name, vNeedClosingTags )) {
					ending = "";
				}
				
				if (ending == null || ending.length() < 1) {
					if (vTagCounts.containsKey( name )) {
						vTagCounts.put( name, vTagCounts.get(name)+1 );
					} else {
						vTagCounts.put( name, 1 );
					}
				} else {
					ending = " /";
				}
				return "<" + name + params + ending + ">";
			} else {
				return "";
			}
		}
		
		// comments
		p = Pattern.compile( "^!--(.*)--$", REGEX_FLAGS_SI );
		m = p.matcher( s );
		if (m.find()) {
			String comment = m.group();
			if (STRIP_COMMENTS) {
				return "";
			} else {
				return "<" + comment + ">"; 
			}
		}
		
		return "";
	}
	
	/**
	 * Process param protocol.
	 *
	 * @param s the s
	 * @return the string
	 */
	protected String processParamProtocol( String s )
	{
		s = decodeEntities( s );
		Pattern p = Pattern.compile( "^([^:]+):", REGEX_FLAGS_SI );
		Matcher m = p.matcher( s );
		if (m.find()) {
			String protocol = m.group(1);
			if (!inArray( protocol, vAllowedProtocols )) {
				// bad protocol, turn into local anchor link instead
				s = "#" + s.substring( protocol.length()+1, s.length() );
				if (s.startsWith("#//")) s = "#" + s.substring( 3, s.length() );
			}
		}
		
		return s;
	}
	
	/**
	 * Decode entities.
	 *
	 * @param s the s
	 * @return the string
	 */
	protected String decodeEntities( String s )
	{
		StringBuffer buf = new StringBuffer();
		
		Pattern p = Pattern.compile( "&#(\\d+);?" );
		Matcher m = p.matcher( s );
		while (m.find()) {
			String match = m.group( 1 );
			int decimal = Integer.decode( match ).intValue();
			m.appendReplacement( buf, chr( decimal ) );
		}
		m.appendTail( buf );
		s = buf.toString();
		
		buf = new StringBuffer();
		p = Pattern.compile( "&#x([0-9a-f]+);?");
		m = p.matcher( s );
		while (m.find()) {
			String match = m.group( 1 );
			int decimal = Integer.decode( match ).intValue();
			m.appendReplacement( buf, chr( decimal ) );
		}
		m.appendTail( buf );
		s = buf.toString();
		
		buf = new StringBuffer();
		p = Pattern.compile( "%([0-9a-f]{2});?");
		m = p.matcher( s );
		while (m.find()) {
			String match = m.group( 1 );
			int decimal = Integer.decode( match ).intValue();
			m.appendReplacement( buf, chr( decimal ) );
		}
		m.appendTail( buf );
		s = buf.toString();
		
		s = validateEntities( s );
		return s;
	}
	
	/**
	 * Validate entities.
	 *
	 * @param s the s
	 * @return the string
	 */
	protected String validateEntities( String s )
	{
		// validate entities throughout the string
		Pattern p = Pattern.compile( "&([^&;]*)(?=(;|&|$))" );
		Matcher m = p.matcher( s );
		if (m.find()) {
			String one = m.group( 1 ); //([^&;]*) 
			String two = m.group( 2 ); //(?=(;|&|$))
			s = checkEntity( one, two );
		}
		
		// validate quotes outside of tags
		p = Pattern.compile( "(>|^)([^<]+?)(<|$)", Pattern.DOTALL );
		m = p.matcher( s );
		StringBuffer buf = new StringBuffer();
		if (m.find()) {
			String one = m.group( 1 ); //(>|^) 
			String two = m.group( 2 ); //([^<]+?) 
			String three = m.group( 3 ); //(<|$) 
			m.appendReplacement( buf, one + two.replaceAll( "\"", "&quot;" ) + three);
		}
		m.appendTail( buf );
		
		return s;
	}
	
	/**
	 * Check entity.
	 *
	 * @param preamble the preamble
	 * @param term the term
	 * @return the string
	 */
	protected String checkEntity( String preamble, String term )
	{
		if (!term.equals(";")) {
			return "&amp;" + preamble;
		}
		
		if ( isValidEntity( preamble ) ) {
			return "&" + preamble;
		}
		
		return "&amp;" + preamble;
	}
	
	/**
	 * Checks if is valid entity.
	 *
	 * @param entity the entity
	 * @return true, if is valid entity
	 */
	protected boolean isValidEntity( String entity )
	{
		return inArray( entity, vAllowedEntities );
	}
	
	/**
	 * In array.
	 *
	 * @param s the s
	 * @param array the array
	 * @return true, if successful
	 */
	private boolean inArray( String s, String[] array )
	{
		for (String item : array)
			if (item != null && item.equals(s))
				return true;
		
		return false;
	}
	
	// ============================================ START-UNIT-TEST =========================================
	/**
	 * The Class Test.
	 */
	public static class Test extends TestCase
	{	
		
		/** The v filter. */
		protected HTMLInputFilter vFilter;
		
		/* (non-Javadoc)
		 * @see junit.framework.TestCase#setUp()
		 */
		/**
		 * Sets the up.
		 */
		protected void setUp() 
		{ 
			vFilter = new HTMLInputFilter( true );
		}
		
		/* (non-Javadoc)
		 * @see junit.framework.TestCase#tearDown()
		 */
		/**
		 * Tear down.
		 */
		protected void tearDown()
		{
			vFilter = null;
		}
		
		/**
		 * T.
		 *
		 * @param input the input
		 * @param result the result
		 */
		private void t( String input, String result )
		{
			Assert.assertEquals( result, vFilter.filter(input) );
		}
		
		/**
		 * Test_basics.
		 */
		public void test_basics()
		{
			t( "", "" );
			t( "hello", "hello" );
		}
		
		/**
		 * Test_balancing_tags.
		 */
		public void test_balancing_tags()
		{
			t( "<b>hello", "<b>hello</b>" );
			t( "<b>hello", "<b>hello</b>" );
			t( "hello<b>", "hello" );
			t( "hello</b>", "hello" );
			t( "hello<b/>", "hello" );
			t( "<b><b><b>hello", "<b><b><b>hello</b></b></b>" );
			t( "</b><b>", "" );
		}
		
		/**
		 * Test_end_slashes.
		 */
		public void test_end_slashes()
		{
			t("<img>","<img />");
			t("<img/>","<img />");
			t("<b/></b>","");
		}
		
		/**
		 * Test_balancing_angle_brackets.
		 */
		public void test_balancing_angle_brackets()
		{
			if (ALWAYS_MAKE_TAGS) {
				t("<img src=\"foo\"","<img src=\"foo\" />");
				t("i>","");
				t("<img src=\"foo\"/","<img src=\"foo\" />");
				t(">","");
				t("foo<b","foo");
				t("b>foo","<b>foo</b>");
				t("><b","");
				t("b><","");
				t("><b>","");
			} else {
				t("<img src=\"foo\"","&lt;img src=\"foo\"");
				t("b>","b&gt;");
				t("<img src=\"foo\"/","&lt;img src=\"foo\"/");
				t(">","&gt;");
				t("foo<b","foo&lt;b");
				t("b>foo","b&gt;foo");
				t("><b","&gt;&lt;b");
				t("b><","b&gt;&lt;");
				t("><b>","&gt;");
			}
		}
		
		/**
		 * Test_attributes.
		 */
		public void test_attributes()
		{
			t("<img src=foo>","<img src=\"foo\" />"); 
			t("<img asrc=foo>","<img />");
			t("<img src=test test>","<img src=\"test\" />"); 
		}
		
		/**
		 * Test_disallow_script_tags.
		 */
		public void test_disallow_script_tags()
		{
			t("<script>","");
			if (ALWAYS_MAKE_TAGS) { t("<script","");  } else { t("<script","&lt;script"); }
			t("<script/>","");
			t("</script>","");
			t("<script woo=yay>","");
			t("<script woo=\"yay\">","");
			t("<script woo=\"yay>","");
			t("<script woo=\"yay<b>","");
			t("<script<script>>","");
			t("<<script>script<script>>","script");
			t("<<script><script>>","");
			t("<<script>script>>","");
			t("<<script<script>>","");
		}
		
		/**
		 * Test_protocols.
		 */
		public void test_protocols()
		{
			t("<a href=\"http://foo\">bar</a>", "<a href=\"http://foo\">bar</a>");
			// we don't allow ftp. t("<a href=\"ftp://foo\">bar</a>", "<a href=\"ftp://foo\">bar</a>");
			t("<a href=\"mailto:foo\">bar</a>", "<a href=\"mailto:foo\">bar</a>");
			t("<a href=\"javascript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
			t("<a href=\"java script:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
			t("<a href=\"java\tscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
			t("<a href=\"java\nscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
			t("<a href=\"java" + chr(1) + "script:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
			t("<a href=\"jscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
			t("<a href=\"vbscript:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
			t("<a href=\"view-source:foo\">bar</a>", "<a href=\"#foo\">bar</a>");
		}
		
		/**
		 * Test_self_closing_tags.
		 */
		public void test_self_closing_tags()
		{
			t("<img src=\"a\">","<img src=\"a\" />");
			t("<img src=\"a\">foo</img>", "<img src=\"a\" />foo");
			t("</img>", "");
		}
		
		/**
		 * Test_comments.
		 */
		public void test_comments()
		{
			if (STRIP_COMMENTS) {
				t("<!-- a<b --->", "");
			} else {
				t("<!-- a<b --->", "<!-- a&lt;b --->");
			}
		}

	}
	// ============================================ END-UNIT-TEST ===========================================
}
