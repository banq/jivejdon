<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>



<!-- second query result -->
<logic:present name="messageListForm">

    <logic:greaterThan name="messageListForm" property="allCount" value="0">

<div id="main-content" class="col-lg-12">
	<div class="box">

        <main>
     
            <logic:iterate indexId="i" id="messageSearchSpec"
                           name="messageListForm"
                           property="list">
                <bean:define id="forumMessage" name="messageSearchSpec"
                             property="message"/>
                <bean:define id="forumThread" name="forumMessage"
                             property="forumThread"/>
                <bean:define id="forum" name="forumMessage"
                             property="forum"/>
                <bean:define id="account" name="forumMessage"
                             property="account"/>
                   <div class="row">
                        <div class="col-lg-12">
                            <header>
                            <h3 class="vid-name">
                            <a href='<%=request.getContextPath()%>/<bean:write name="forumThread" property="threadId" /><bean:write name="forumThread" property="pinyinToken" />.html'
                               target="_blank">
                                <bean:write name="forumThread" property="name"/></a>
                            </h3>    
                            </header>    
                            <div>
                               
                                <div>

                                    <bean:write
                                        name="messageSearchSpec"
                                        property="body"
                                        filter="false"/>
                                    <bean:define id="adIndex"
                                                 name="i"
                                                 toScope="request"/>

                                </div>
                              
                            </div>

                            <p><span class="blackgray"><bean:write
                                    name="forumMessage"
                                    property="modifiedDate3"/></span>


                        </div>
                  
                </div>


            </logic:iterate>
       
        </main>    
        
     


</div>
</div>
    </logic:greaterThan>
       
</logic:present>
</div>
</div>
</div>
</div>
