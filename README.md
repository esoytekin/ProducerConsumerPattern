# Producer Consumer Pattern Implementation in JAVA

a basic multi-thread producer consumer implementation in java with using `java.util.concurrent.BlockingQueue`

commits are in order that each handles a specific problem when implementing this pattern.

1. initial implementation of a blocking queue with single producer and single consumer
    - has the problem to process all the tasks in the queue
2. fix for processing all the tasks
    - has the problem for termination 
3. fix for termination problem
    - does not terminate if there are multiple consumers 
4. simulating long running process
    - shows that tasks are running in a single consumer thread rather than multiple consumers
5. adding multiple consumers
    - has the problem of terminating again
6. added poison pill for successful termination.

if you have questions or suggestions for improve please don't hesitate to comment by opening an issue or sending me a [mail](emrahsoytekin@gmail.com).



