Maximilian Thompson
mthompson2

Section I:
    In a command prompt in this directory, execute the following:
        >javac *.java
        >java Project2

Section II:
    As far as I'm aware, all parts of my project are working. However, the
    parsing of user input may fail under certain conditions that I elaborate
    on in section III.

Section III:
    For this project I am working under the assumption that the hypothetical
    DBMS can cache a single block, and that this cache is cleared between commands.
    I made this decision for two reasons. First, caching only a single block is
    simpler and the focus of this project is not on the cache like the first
    project. Second, clearing the cache between commands gives a better feel
    for how big the difference between an index scan and full table scan is
    because neither may accidentally benefit from side effects of the previous
    command.

    Additionally, because so much of the project specifications is hard coded,
    I was able to make some pretty major simplifications in terms of how the
    program interacts with files and how the program interacts with the user.
    For one, the program does not acknowledge changes in any part of the command
    other than the parts specified to change (i.e. it parses the values in the
    command, but will not recognize a command where the order of > and < is
    reversed as a valid command). Also, because the program has no way of
    modifying files, I don't keep track of a dirty bit or anything.

    I don't know if this differs from the assignment or not, but because the
    indexes are kept in memory, I don't count interaction with them as accessing
    a block, and only count the point where records are accessed as accessing
    a block.

    I have implemented an exit command for exiting the program slightly more
    gracefully than ^C.