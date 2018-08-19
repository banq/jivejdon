package com.jdon.jivejdon.auth.jaas;

import java.security.Principal;

public class User implements Principal {
	
   private String username;
   
   public User(String username){
      this.username=username;
   }
   /**
    * @param object Object
    * @return boolean
    * @todo Implement this java.security.Principal method
    */
   public boolean equals(Object object){
      boolean flag=false;
      if(object==null)
         flag=false;
      if(this==object)
          flag= true;
      if(!(object instanceof User))
         flag= false;
      if(object instanceof User){
         User that = (User) object;
         if (this.getName().equals(that.getName())){
            flag= true;
         }
      }
      return flag;
   }
   /**
    * @return String
    * @todo Implement this java.security.Principal method
    */
   public String toString(){
      return this.getName();
   }
   /**
    * @return int
    * @todo Implement this java.security.Principal method
    */
   public int hashCode(){
      return username.hashCode();
   }
   /**
    * @return String
    * @todo Implement this java.security.Principal method
    */
   public String getName(){
      return this.username;
   }
}
