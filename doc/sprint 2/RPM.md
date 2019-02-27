*NOTE: System design document underwent no changes this phase. A single paragraph explanation- as the professor required- has been attached to the bottom of the document.*

# Release 2 Planning Meeting - QuickHealth
---
##### Feb 25th 2019 | 4:00PM - 6:00PM
***
### Participants:
***
##### Vaishvik Maisuri, maisuri9
##### Roshaan Faisal, faisalro
##### Wen Tong, tongwen
##### Christine Taing, taingchr
##### Qurat-ul-ain Afzal, afzalqur
##### Parth Brahmbhatt, brahmb24 
##### Ibrahim Abdi, abdiibra
***

### Unfinished tasks:


As before, these tasks are accordingly represented in PivotalTracker. 

### Meeting Overview:

In this meeting, the QuickHealth team reflected on Sprint 1, and what went well and what didn't. The entire team was present for the meeting (from Feburary 25, 2019, 4:00PM - 6:00 PM).

We realized that we over commited in sprint1, and assigned more user stories than we were able to complete. Our main concerns and focus was to learn the new technologies. In this sprint, we agreed that we needed to focus on finishing user stories that we have already asigned in full before starting other user stories.

In our reflection, we identified several strengths of our developmental approach from sprint1 which we agreed to continue:
- Consistent votes and in depth discussions about implementation to ensure all members voices are heard and the best methodology is adapted
- Ensuring all team member shares the same coding environment and have all keys and urls required to collaborate on all environments
- Ensuring all members share the deadline by which they will complete their assigned tasks and verify when all tasks are completed
- Ensuring the product maintains a consistent user interface and done.md from sprint 0

The main reason for our initial setback was due to the need to learn new technologies. The subgroups working on the account registration, profile setup and medical history form user stories were unable to implement MongoDB on time, so those user stories were not able to be implemented on time for sprint 1.
We also decided that members from different teams need to communicate and work together more as a lot of members were facing the same problems, and working together allows us to come up with a solutions faster. Therefore our group was able to finish many features in this sprint.

Consequently, sprint 2 provided us with the most significant highlights of the project so far. It was able to give the group an idea of how the user interface will work.  Therefore, we updated our product backlog's weightings, strictly increasing the weight of tasks we initially underestimated - such as identification, SOS signal cancellation. Our group decided on increasing the weight through a group vote due to the new insight through feature development in sprint 2. Aside from the removal of the completed task, the backlog did not require much change.

Our best experiences included the completion of the location direction feature. It was motivating to be able to see the working results. The system architecture diagram was very helpful in understanding the technologies available to us and how they interact. Finally, finishing our initial CRC model provided us one of our best experiences in sprint 1 as well. After that was designed, our group envisioned our code base with a much greater clarity than before, and it was a fun experience coding with it on hand.

### Release Goals and Plans 
1. For this release, we have selected to focus on the following user stories: 
	- SOS Signal 
	- Medical history form
	- Unique custom username 
	- Secure password 
	- Profile setup
	- Registration

2. The main focus will be to finish all user stories assigned in each subteam's individual branches, and combine them into one executable application on the main branch. This ensures that all members are fully aware of how their feature interacts with the main application and solidifies their understanding of all user stories. Merging completed user stories as soon as possible will allow us to resolve any merge conflicts early on in the development process instead of pushing it into later sprints.

3. Our plan for this release is to implement the backend for basic authentication, and continue to implement the core feature, adding on more functionalities to it. The user should be able to go through the whole registration and sign in process with ease and security. Since we were able to get the direction of the user in sprint 1, the user should now be able to send a SOS signal with indications of their location, to the server and stored on the application's database.


### Meeting Conclusion:
QuickHealth's first SCRUM meeting was a successful gathering where our team members were able to find our bearings again. We noted our past mistakes and attempted to ensure we would not repeat them in the future. 

### SYSTEM DESIGN EXPLANATION:

Our System design Document underwent no changes. 
As the majority of this sprint was dedicated to finishing user stories picked in the previous sprint. 
Therefore, the previous design that was created was perfect and did not require any change. 
Also, this means that there were no changes made to the underlying project architecture. Instead, our development was mainly based on enacting the CRC's design of sprint 1. 
As we start to better integrate and combine features through the navigation bar, we assume we will change our system design, as group members have decided on possible new features and updates on a few features causing our CRC cards to be updated and certain characteristics of our DAO model to be upgraded to improve our code. For now, our objective remains to implement our original design idea using the techincal stack we intend to use which was Android Studio, MongoDB, Express, and Node.








