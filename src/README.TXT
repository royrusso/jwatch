For information about JWatch, please visit the JWatch Home: http://code.google.com/p/jwatch/

Installation and Configuration Options can be found here:

http://code.google.com/p/jwatch/wiki/Installation


For Liveops's we have modified JWatch to have the abiity to pause, resume, and delete scheduled jobs.

JWatch accesses Quartz thru a JMX connection. JMX must be enabled on the Quartz container, the scheduler
instantiated within Quartz must be JMX enabled, and firewalls from the JWatch monitor to the Quartz 
servers must have the configured JMX port open.



JWatch is an open source Quartz monitoring application. It access quartz schedulers using JMX. The home page for the project is at: http://code.google.com/p/jwatch/

We have take this project and enhanced it is a couple of ways to meet the requirements at LiveOps.

1.  Add the capability to pause, resume, and delete scheduled jobs.
2.  Change the GUI to present Data bout the account, call center and other information available to the user.
3.  Bug fixes and other changes.

