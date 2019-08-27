# WynnAloud
WynnAloud - The quest voice over mod for the Wynncraft Minecraft Server. 

## Committing
If you are committing  to the project please make sure you read the information below for that is suitable to your situation 


### Voice Lines and Code
Thank you for choosing to help with the project! Adding voice lines can be a tedious process but it will begin to become second nature in no time. Before recording please read the 'Just Voice Lines' section first.
Steps:
- Convert the audio file to the '.ogg' file format. It is recommended to use this website to convert the file: https://convertio.co/mp3-ogg/
- Name the audio file in accordance to the rules:
  - TheNpcName + A letter indicating is order
  - All lowercase letters
    - For Example:
    - ragnikinga.ogg - Would be the first voice line said by Ragni King
    - ragnikingb.ogg - Would be the seccond voice line said by Ragni King
- Place the audio file in 'src/main/resources/assets/wynnaloud/sounds'
- Register the sound in the 'sounds.json' file in the 'src/main/resources/assets/wynnaloud' directory. There is more information on how to do this in the file.
- Register the sound in the 'ChatEventHandler' class' Sound Registry. There is more information commented in the class.
- Add the sound into the 'ChatEventHandler' class under the 'VOICE LINES' comment, for more help refer to the 'ADDING VOICE LINES HELP AND EXAMPLE' comment just above.

Thats it Thank you!
	
### Just Voice Lines
If you are do not know how to code in the voice line don't be scared! All you can just contribute voice lines by following the steps below. 
Steps:
- Recording your line!
  - When recording your line please try and eliminate echoing, background noise and popping on the microphone. Some clips may be discarded if they are not of high enough quality.
  - Your clip should contain all the text in the dialog block. For instance: quest lines [1/7] - [7/7] should be the only things in a singular audio file.
  - Try to do all the lines for the one character, this includes even the ones that you may not think of like:
    - Under leveled lines
	- During quest lines
	- Finished quest lines
	- And any other dialog
  - Do any editing you may want done yourself, other people will likely not do audio editing for you.
  - Be enthusiastic and take on your character! Put yourself in the shoes of the character your are impersonating, think how they would be feeling and portray it in their voice.
- Committing your files:
  - Make sure you read the 'README.txt' in the 'To Be Added' Folder. It will give you all the information you need.
  - Now you have recored your lines commit them to the 'To Be Added' folder, along with the text file mentioned in the 'README.txt' file.
  - If any of the steps are not done correctly then when someone adds your line it will not work correctly or will not be added at all.
  


### Just Code
Members of the community who just want to contribute voice lines will commit them to the 'To Be Added' folder, you can use these and add them in. 
Steps:
- Find a valid text file in the 'To Be Added' folder, it must contain all the elements outlines in the 'README.txt' file of the same directory.
- Do all the steps in the 'Voice Lines and Code' section above.
- Delete the both the files from the 'To Be Added' directory
  - This is so another person does not try to add it again.
  
