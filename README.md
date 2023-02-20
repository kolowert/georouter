## City Router Project

Here is going to be solution for the task of a traveling salesman

Optimizer works on Johnson-Trotter algorithm

https://en.wikipedia.org/wiki/Steinhaus%E2%80%93Johnson%E2%80%93Trotter_algorithm


### Implementation of Johnson-Trotter algorithm in a non-recursive form.

Associate each element of the permutation p[i] with direction pointer d[i].

We will indicate the direction using the arrows ← ("left") or → ("right"). 

We call an element movable if there is an element smaller than it in the direction of the arrow. 

For example, for p={1,3,2,4,5} and  d={←,→,←,→,←}, moving elements are 3 and 5.

At each iteration of the algorithm, we will look for the largest movable element and swap with the element that is in the direction of the arrow. 

After that, we change the direction of the arrows to the opposite for all elements larger than the current one.
Initially p={1,…,n}, d={←,…,←}.

#### Example for n = 3
> p={1,2,3}; d={←,←,←}
>
> p={1,3,2}; d={←,←,←}
> 
> p={3,1,2}; d={←,←,←}
> 
> p={3,2,1}; d={→,←,←}
> 
> p={2,3,1}; d={←,→,←}
> 
> p={2,1,3}; d={←,←,→}
>

### Concurrency realisation
planned to be implemented...

