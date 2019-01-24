OBJECTIVES
----------
###Purpose 
1. Save Lives 
2. Help doctors reach injured individuals quickly

###OverView
1. Setup the account management system and (restricted) normal user access. 
2. Allow any user to ask for help using the SOS signal. 
- Choose the type of injury and send the location to nearby doctors 
- Simple, Quick and User-friendly UI which enables doctors to reach an injured individual. 
3. Allow for Doctors to view all SOS signals on a map near his location.   

###Details
1. We plan to develop an account management system that allows for three different registration process. A user must be a registered user to be able to use QuickHelp. During the registration process, the user has a choice to register as a skilled individual or a doctor. If neither choice applies, then they are just a normal user. If a user decides to also register as a skilled individual or doctor, they are required to provide official documents, and certifications to prove their eligibility to assist injured individuals in Canada. 
2. Our goal is to provide users with a simple to use and minimalistic interface. The user should be able to navigate around the app and be able to request for help during an emergency quickly and also send the injured individuals information so that he easier to locate. A simple user interface will reduce loading time and allow for users to navigate around the app and process requests more quickly.
3. We plan to develop a UI that displays a map similar to Google maps with locations of all SOS signals with colours from green to red marking the severity of the SOS signal. Which will allow doctors to click on the SOS signal and check information and accept or reject the request? On accepting, the map will give the Doctor the fastest route to the patient. 

---
Key Users
---------
###OverView
1. Normal User
2. Skilled Individuals
3. Doctors

###Details
1.  The Injured Patient is meant to describe every individual in the world that requires medical aid. Also, the individual must have access to a phone to use the phone. 
2.  The Skilled Individual is meant to describe a single individual who has registered with the app and has a verified certificate to perform a medical treatment in Canda. This individual will be able to receive SOS signals so that they can help an injured patient calling for help. 
Example: CPR treatment certificate by a Lifeguard. 
3. The Doctor is meant to describe a single individual who has registered with the app and has a verified document proving they are practicing doctor in Canda. Similarly to Skilled Individuals, They will be able to receive SOS signals. 
Example: 1. Critical Care Medicine Specialists: who take care for people who are critically ill or injured. 
2. Family Physicians: who take care of primary illness 
3. Physiatrists: who take care of sports injuries. 

---
Scenarios
---------
###OverView
1. User friend is choking at a restaurant because he swallowed a big piece of meat.

2.  User dislocates his arm and needs assistance with keeping his arm in the right position to lessen the pain.

3.  The user faces a life-threatening situation where he stabbed and need a professional doctor to help keep him from bleeding to death.  

###Details
#### Scenario 1

My friend is choking at a restaurant, and we need someone to perform Abdominal thrusts, and the ambulance is too far away. I requested for assistance using QuickHealth which finds the closest person with medical training (if they are registered on the app). John, who has Abdominal thrusts practice, receives a request where he can accept/decline if he has the time to save my friend or at least help out. He can also use the app's navigation feature to get detail location and how to get to my friend.

#### Scenario 2 

I was leaving UTM after a late night study session in the library. As I am walking down the stairs to go to the parking lot, I tumbled down the stairs and dislocated my arm, and I started bleeding everywhere. There is no one around to help, so I decided to use QuickHealth. The app was able to alert for an ambulance and also provide tips and instructions on what I could do to reduce the bleeding, while I wait for the ambulance to come. 

#### Scenario 3

My friend and I were walking down through the park at midnight, and some person came and threatened us with a knife. We gave our belongings to him, but the robber still attacked my friend. He stabbed my friend on the shoulder and left him bleeding. It was a late night, so we decided to use the app to contact any skilled doctors in the area. Dr. Brown received an alert that woke him up. The alert described the location and type of injury. He figured he could help, so he made his way to us and helped slow the bleeding until the ambulance arrived. 


---
Principles
---------
###OverView
1. Intuitive, easy to navigate UI
2. Instant and simple navigation for the doctor to injured individual 
3. Quick signal to doctors
4. Notify the doctor if the ambulance already is assisting the injured individual 
5. All doctors and skilled individual are checked with proper documentation.


###Details

1. An easy one-click navigation with minimalistic typing is required so that no time is wasted when contacting an individual for help.  Unnecessary features and information can clutter the interface and confuse the user at a crucial time of need.

2. After a doctor or skilled individual has accepted to help a patient requesting help, that individual should be guided to the patient using a simple to follow direction which is also the fastest way to reach the patient. 

3. The SOS signal should be of the highest priority when making calls to the server so that the SOS signal is uploaded to the app's map so that skilled individuals and doctors can view the SOS signal on their app. 

4. Don't want to waste the Doctors time, so that we unable the injured user to specify when an ambulance has already reached him and or if he has already found help. In short, if the patient is currently safe then cancel the SOS signal. 

5. we need to enable the app to take pictures and upload documents so that we can verify a skilled individual or doctor. 











