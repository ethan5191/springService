Please read me.

I made a some assumptions when creating this minor project. 
1. I assumed this was a spring rest web service, that would be called vai its URL. Therefore I used an @RestController with an @GetMapping controller method. I do not have any UI elements as I understood this to be a backend only change.
2. I assumed that this was being added to an existing application. So i didn't do anything with creating a spring-context or spring boot startup. I didn't think any of that was necessary for this, please correct me if I am wrong.
3. I setup the EntityManager and Hibernate stuff as I have created it in my local messing around with both projects. Odds are these process are not following best practices, as I have no real world experience with either of these technologies. I only know what I have taught myself.
4. I wasn't sure what data type the 'invoice_data' element could be pulled down as. I assumed its a varchar, but wasn't sure if it could be traeted as a Map or if there is a JSON library that could parse it cleanly. Due to this I treated it as a spring and parsed it where I needed.
5. I don't have any experience mocking connections to a database and due to time constraints, I didn't get around to trying to figure it out. 
