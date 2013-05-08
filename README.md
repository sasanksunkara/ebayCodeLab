ebayCodeLab
===========

The directory RemoteSync has the source files for RemoteSync and implementation of MetricsDao. RemoteSyc works by spawning
a thread to retrieve metrics from nodes and writing to a database.
Under directory UnitTest are two test cases for MetricsDaoImpl and RemoteSyncTest. They test/validate insertion of records
for each node. 

Database schema is assumed to have two tables CpuInfoTable( Node, Core Id,Timestmap, Utilization)  and 
MemInfoTable (Node , Timestamp, Used, Free) . Database connectivity is achieved by using JDBC. Username,password and driver to the database have been left in the code.

