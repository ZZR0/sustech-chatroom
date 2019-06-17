# main-project-repository-sustech-chatroom
main-project-repository-sustech-chatroom created by GitHub Classroom   
Group name : Sustech Chatroom   
Group member :

		11612512陈达寅
		11610528潘天赐
		11610507陈逸鸣
		11612527曾政然
		11612729查梓杰
		11612601何治成

1. The GUI of Registration and login and some click events (11612512 Dayin Chen)   
2. The Server of Registration and login (11612527 Zhengran Zeng)   
3. Read the content of Textfield and send to the server. Some dialog to forbiden the illegal operations (11612512 Dayin Chen)   
4. JAVADoc (11612729 Zijie Zha)
5. Fixing Checkstyle,PMD,Findbugs erros(11610528 Tianci Pan)
6. Camera feature (11612601 Zhicheng He)

a) Chosen Features  
	/In this iteration we chose to develop the registration function, login function, camera function. First of all, from the development point of view, user data as the index of data, other functions rely on user data for the specified push, so the user datas' manipulation must be completed in the first time, and from the perspective of the user interface, the first thing that users would do for entering the application is to register or log in, so these two functions should be completed first. The only one in the remaining functions that can be developed in parallel with the login registration function are cameras and translations. The camera is easier to implement, so the third feature chosen to be completed is the camera.
	
b) Test scenario   
	+ Register   
		+ When user successfully register an account, a success dialog is shown   
		+ When user fail to register due to inconsistency between two password or existed username, corresponding dialog is shown   
	+ Login   
		+ When user login successfully to her account, main page of chatroom is shown   
		+ When user login with an incorrect userid/password, the user will get an error message saying    
			“Wrong password”   
	+ Taking photo   
		+ When successfully takeing a photo, it will turn back to main page of chatroom and tell the user the storage location   
		+ User can also leave from the camera fragment to main page directly without taking a photo.   

​		 

d) Checkstyle, PMD, Findbugs   

The result of checkstyle:
![image](img/checkstyle.png)   
****
The result of findbugs:
![image](img/findbugs.png)   
****
The result of PMD:
![image](img/PMD.png)   
Out project only have this error because the original API of camera is big and complex. After our decoupling, it still seems to be a god class to PMD. But most of method it used is override or private, we can't really depart it into many parts and preserving our functional requirement.

e)
Both server and app document will be seen under Documents file.

g) Future plan:   
		week10: Account register and Login form and Camera.   
		week11: Chating room and Friend system   
		week12: Translation and File transition   
		week13: Moments sharing and Comment or Like the Moment   
		Week14: Extra effect on GPA   
		Week15: Polish the GUI and the other details   
