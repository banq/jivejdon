
<%@ page import="java.util.*,java.io.*,java.net.*,java.net.InetAddress;"%>
<%

    /**************************************************************************
     *  InMobi Ad Code
     *  Copyright mKhoj Solutions Pvt Ltd and all its subsidiaries. All rights reserved.
     **************************************************************************/

    /************************************************************************** 
     *  For better targeting, tell us where this ad is intended to 
     *  be placed on the page.
     *  Accepted Values are 'top', 'middle', 'bottom', or 'page',
     *  denoting top 20%, middle 60%, bottom 20% or the whole page
     *  length respectively.
     **************************************************************************/
    String mkhojPlacement = "page";

    /**************************************************************************
     *  Make the value of this parameter true if you are running some tests.
     *  This will make the ad code call the mKhoj sandbox.
     **************************************************************************/
    boolean mkhojTest     = false;

    /**************************************************************************
     *  For better targeting, track individual users by setting a persistent
     *  cookie/session in their browser. Set the cookie/session with following 
     *  properties
     *    - Cookie value : UUID or some other unique value
     *    - Path         : /
     *    - Expires      : 1 year
     *  On a request, if cookie/session value is not retrieved, set a new value.
     *  Set this value in the following variable.
     *  NB: This has to be done near the top of the page before any HTML body
     *      fragment goes out.
     **************************************************************************/
    String mkhojSessionId = "";
    
    /**************************************************************************
     *  ALL EDITABLE CODE FRAGMENTS ARE ABOVE THIS MESSAGE. DO NOT EDIT BELOW
     *  THIS UP TO NOAD SECTION.
     **************************************************************************/
  
    String mkhojPublisherSiteId = "4028cbff3aab0518013addbaf28603a4";
    if( null!=mkhojPublisherSiteId )
    mkhojPublisherSiteId = URLEncoder.encode( mkhojPublisherSiteId, "UTF-8" );

    String mkhojHandset  = request.getHeader("User-Agent");
    if( null!=mkhojHandset )
        mkhojHandset     = URLEncoder.encode( mkhojHandset, "UTF-8");

    String mkhojCarrier  = request.getRemoteAddr();
    if( null!=mkhojCarrier )
        mkhojCarrier     = URLEncoder.encode( mkhojCarrier, "UTF-8");

    String mkhojVersion  = "el-OC06-CTATE-20090805";
    if( null!=mkhojVersion )
        mkhojVersion     = URLEncoder.encode( mkhojVersion, "UTF-8" );

    String mkhojReferer  = request.getHeader("Referer");
    if( null!=mkhojReferer )
        mkhojReferer     = URLEncoder.encode( mkhojReferer, "UTF-8" );

    String mkhojAccept   = request.getHeader("Accept");
    if( null!=mkhojAccept )
        mkhojAccept      = URLEncoder.encode( mkhojAccept, "UTF-8" );

    String pageUrlQuery  = request.getQueryString();
    String mkhojPageUrl  = request.getRequestURL().append( null!=pageUrlQuery?
                           ("?" + pageUrlQuery):"" ).toString();
    if( null!=mkhojPageUrl )
        mkhojPageUrl     = URLEncoder.encode( mkhojPageUrl, "UTF-8" );

    Enumeration mkhojXHeaders  = request.getHeaderNames();
    String mkhojXHeadersString = "";

    while( mkhojXHeaders.hasMoreElements())
    {
       String mkhojHeaderName  = (String)mkhojXHeaders.nextElement();
       String mkhojHeaderValue = request.getHeader( mkhojHeaderName );
       mkhojHeaderName         = mkhojHeaderName.toLowerCase();

       if( null!=mkhojHeaderName && mkhojHeaderName.startsWith("x-") )
       {
           if( null!=mkhojHeaderValue )
              mkhojHeaderValue = URLEncoder.encode( mkhojHeaderValue, "UTF-8" );
           mkhojXHeadersString = mkhojXHeadersString + "&" + mkhojHeaderName + 
                                 "=" + mkhojHeaderValue ;
       }
    }

    String mkhojUrlString       = mkhojTest?
                                  "http://w.sandbox.mkhoj.com/showad.asm":
                                  "http://w.mkhoj.com/showad.asm";
      
    String mkhojPostContent     = "mk-siteid="     + mkhojPublisherSiteId + 
                                  "&h-user-agent=" + mkhojHandset +
                                  "&mk-carrier="   + mkhojCarrier + 
                                  "&mk-placement=" + mkhojPlacement + 
                                  "&mk-sessionid=" + mkhojSessionId + 
                                  "&mk-version="   + mkhojVersion + 
                                  "&h-page-url="   + mkhojPageUrl + 
                                  "&h-referer="    + mkhojReferer + 
                                  "&h-accept="     + mkhojAccept + 
                                  mkhojXHeadersString ;

    URL mkhojUrl= new URL( mkhojUrlString );
    
    HttpURLConnection mkhojMyRequest = ( HttpURLConnection )mkhojUrl
                                         .openConnection();

    mkhojMyRequest.setConnectTimeout( 5000 );
    mkhojMyRequest.setReadTimeout( 5000 );
    mkhojMyRequest.setDoInput( true );
    mkhojMyRequest.setDoOutput( true );
    mkhojMyRequest.setUseCaches( false );
    mkhojMyRequest.setRequestProperty( "Content-Type",
                                       "application/x-www-form-urlencoded" );
    mkhojMyRequest.addRequestProperty( "X-mKhoj-SiteId", mkhojPublisherSiteId );
    mkhojMyRequest.setRequestProperty( "charset", "UTF-8" );
    mkhojMyRequest.setRequestMethod("POST");

    String mkhojContents = "";

    try
    {
        DataOutputStream  mkhojPostStream = new DataOutputStream( 
                                            mkhojMyRequest.getOutputStream() );

        mkhojPostStream.writeBytes( mkhojPostContent );

        PrintWriter       mkhojWriter         = response.getWriter();
        InputStream       mkhojInStream       = mkhojMyRequest.getInputStream();
        InputStreamReader mkhojStreamReader   = new InputStreamReader( 
                                                       mkhojInStream );
        BufferedReader    mkhojBufferedReader = new BufferedReader( 
                                                 mkhojStreamReader);

        String mkhojLine = null;

        while ( null != (mkhojLine = mkhojBufferedReader.readLine()) )
        {
            mkhojContents += mkhojLine;
        }

        if( null != mkhojContents )
        {
            mkhojWriter.println( mkhojContents );
        }
    }
    catch (Exception ex)
    {
    }

    /*************************************************************************
     *  THIS 'IF' BLOCK CONFIRMS IF THERE WASN'T ANY AD. USE THIS BLOCK FOR
     *  BACK-FILLING THIS PLACE OR ENTER SOME OTHER COMPATIBLE WAP HREF TAG
     *  HERE TO SHOW TEXT/URL. E.G. <a href="http://www.mkhoj.com">mKhoj</a>
     *************************************************************************/
    if( null != mkhojMyRequest.getHeaderField("X-MKHOJ-NOAD") )
    { 
    }
%>
