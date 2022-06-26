# GRAPH implementation :disappointed_relieved:

In	 this	 project, was used a resource allocation graph,	which	consists of a	directed graph in	which	each	node	can	be	either	a	process	or	a	resource,	and	edges	indicate	whether	a	process	is	waiting	for	a	resource	(edge	from	the	process	to	the	 resource)	or	whether	a	resource	is	assigned	to	a	process	(edge	from	the	resource	to	the	process).

## Deadlock and its handling

In that programm the deadlock represents the situation, when the loop is created in the graph. So that the process won't be able to move forward, because of waiting for the other process's resource, which in turn waits for the resource of the previous.

Theare are several ways for dealing with Deadlocks, that are represented above. For that realisation was used the last one.

### 1.Ignoring	deadlock :cold_sweat:
in	this	approach,	it	is	assumed	that	a	deadlock	will	never	occur (or	have	a	
very	low	probability to	happen).
### 2.Detection and recovery :astonished:
under	deadlock	detection,	deadlocks	are	allowed	to	occur.	Then	the	 system’s	state is	examined	to	detect	that	a	deadlock	has	occurred, and	subsequently, it	is	corrected.
### 3.Deadlock	Prevention :flushed:
prevents one	of	the	four	Coffman	conditions	from	occurring (if	possible).	
### 4. Deadlock prediction :smiley_cat:
the	operating	system	tries	to	detect	whether	the	probability	of	deadlock	is	high,	and	if	so,	it	tries	to	run	the	processes	involved	in	a	way	that	avoids	deadlock.	The	problem	with this	strategy	is	that	there	may	be	no	way	to	predict	how	the	access	to	resources	will be.


## Authors

* **Daria Melnyk** - *Initial work* - [DashulyaMelnyk](https://github.com/DashulyaMelnyk)

## Example of processed file (fragment)

PROCESS P3

RESOURCE R2

RESOURCE R4

OPEN P3 R2

OPEN P3 R4

PROCESS P4

RESOURCE R7

OPEN P4 R7

PROCESS P5

RESOURCE R5

OPEN P5 R5


## Result of work:


DEADLOCK ALERT! PROCESS P4 TRIES TO OPEN RESOURCE R4. THAT WILL CREATE A DEADLOCK!

Resource R4 will be added to process' P4 waiting list!


Graph

process P3 owns R5

resource R2 assigned to P3

resource R4 assigned to P3

process P4 waiting for R4, R8

resource R7 assigned to P4

process P5 owns R7

resource R5 assigned to P5

process P2 owns R4

resource R1 assigned to P2


