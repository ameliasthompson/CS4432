Maximilian Thompson
mthompson2

Section I:
	In a command prompt in this directory, execute the following:
		>javac *.java
		>java Project3

Section II:
    As far as I'm aware, all parts are working. The only thing to note is that,
    while most of the commands have been implemented in a way that's case-insensitive,
    the hash aggregation command requires SUM, AVG, and A or B to be in all caps.
    I know why this is happening, but it does not seem important enough to fix.
    Additionally, it will not accept aggregation without at least one of SUM and
    AVG.

    Also note, the nested-loop join takes a while to run and it doesn't show any
    output until it finishes. Give it a bit of time (my tests took about 67000ms
    to run the command).

Section III:
    I do not sort the buckets in the hash join.

    I ported DBUtil from my solution to project 2 and made some minor modifications
    to it. For the most part all reads in the project use DBUtil which maintains
    a single internal buffer that I reset at the beginning of each command. The
    one exception to this is the nested-loop join which handles loading files
    on its own to match the buffer requirements of the part.

    For aggregation, I use the inbuilt java.util.Hashtable to store groups. I
    have chosen to do this because that part did not specify a number of buckets
    and I didn't want to modify my implementation of the hash table to take a
    type argument on definition.