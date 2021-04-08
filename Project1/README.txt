Maximilian Thompson
mthompson2

Section I:
	In a command prompt in this directory, execute the following:
		>javac *.java
		>java Main <frames>

Section II:
	All provided test cases pass.

	As malformed inputs were specifically stated to not be part of the test
	cases, I have not done anything to handle them for the most part. They will
	generally cause the program to crash.

Section III:
	There are a few key differences. First, when IO isn't performed, my
	implementation prints nothing rather than printing that IO wasn't performed.
	(A line is still printed if IO *is* performed.) Second, my output is
	generally broken up into several lines because I print from several
	different locations in the program. Finally, there is an "exit" command.
	Using the exit command will write all dirty frames to disk before closing.
	It will not do this if you just close the command prompt without running
	exit.

	Additionally, I work with references to frames rather than indexes in the
	frames array most of the time, and in places where the guidelines suggest
	the use of an initialize() function, I use a constructor instead. I changed
	the access to the pin flag to public because I ended up with a getter and
	a setter that did nothing other than get or set.

	In BufferPool, I convert the one indexed record and block numbering system
	into a zero index system where applicable because it plays a lot more nicely
	with arrays. All output still uses the one indexed system.

	I have also implemented a Parser class just for helping out with parsing
	command inputs.