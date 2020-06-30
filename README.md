MailBot
=======

This application was formed the user interface and a key system element in the prototype for an autonomous mail delivery system named "MailBot".
The main code and layout resources can be found here `interface/mailbot/app/src/main/` At a high-level view, the application had two modes of 
operation: collection mode and delivery mode. 

Collection Mode
---------------
In collection mode, the robot would collect packages that were to be delivered to specific locations on its mail delivery route. The person sending 
the item on for delivery would provide the following information on request: 

* Sender Name 
* Sender Email Address
* Recipient Name
* Recipient Email Address
* Delivery Location

The package is given a PIN code and a locker number depending on the availability in the robot. These seven pieces of information are held in a local 
database in the application created using the _Rooms_ functionality in Android Studio (a SQLite database). Data on all stored packages is stored in a 
database. When all lockers are full or a specific time slot is reached, MailBot will proceed on its delivery route. 

Delivery Mode
-------------

The path that MailBot takes is planned by a laptop that sits within the system. The laptop maintains contact with the tablet running this application 
using Bluetooth. Once a location is reached, the laptop will broadcast this update to the application. Upon receiving this, the application and will 
query the database and retrieve data for all packages associated with this location. It sends emails to the sender and recipient notifying them that the 
package has reached its destination. The recipient is instructed how to remove the package and once all mail due to be delivered to that location has 
been offloaded, MailBot will proceed to its next destination. 


Other 
-----
Android application created for the Lenovo Tab 2 A-10 70F (Android Version:6), on Android Studio (3.1).
The Android Studio Project folder is: `MailBot/interface/mailbot`. This can be used to export an .apk for installation.
`MailBot/interface/Bluetooth` contains old bluetooth testing scripts.
