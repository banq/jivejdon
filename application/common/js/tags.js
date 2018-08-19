
function getTagSubCount(contextPath, subscriptionCount, tagID){
     var spanId = 'count_'+ tagID;
     if (subscriptionCount == -1){
        var pars = "tagID=" + tagID;
        new Ajax.Updater(spanId, contextPath +'/query/tagSubcountAction.shtml', { method: 'get', parameters: pars });
     }else if (subscriptionCount > 0){
        $(spanId).innerHTML = '<a href="'+ contextPath +'/social/contentfollower.shtml?subscribedId='+ tagID +'" target="_blank"  rel="nofollow" >'
        	              +subscriptionCount + "人关注";
     }
 }
 
function tagSubCount(contextPath, tagID){	
   var countTag = 'count_' + tagID;
   var pars = 'method=computeSubscriptionNumbers&tagID=' + tagID;
   new Ajax.Updater(countTag, contextPath +'/query/tagSubsCount.shtml', { method: 'get', parameters: pars });
   
} 