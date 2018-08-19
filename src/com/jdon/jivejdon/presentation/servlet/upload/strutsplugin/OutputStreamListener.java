/* Licence:
*   Use this however/wherever you like, just don't blame me if it breaks anything.
*
* Credit:
*   If you're nice, you'll leave this bit:
*
*   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
*   email : plosson@users.sourceforge.net
*/
/*
*  Changed for Part 2, by Ken Cochrane
*  http://KenCochrane.net , http://CampRate.com , http://PopcornMonsters.com
*/
package com.jdon.jivejdon.presentation.servlet.upload.strutsplugin;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Original : plosson on 04-janv.-2006 9:59:27 - Last modified  by $Author: plosson $ on $Date: 2006/01/05 10:09:38 $
 * @version 1.0 - Rev. $Revision: 1.1 $
 */
public interface OutputStreamListener
{
    public void start();
    public void bytesRead(int bytesRead);
    public void error(String message);
    public void done();
}
