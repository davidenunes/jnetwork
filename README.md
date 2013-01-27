# B-Have Network Library
A simple lightweight **network / graph library** written in Java. This is a software library that provides a simple set of tools 
to model and analyse data that can be represented as a graph or network. It provides facilities to treat a network
either as directed or undirected using the same simple `Network` API. 

This library hides the implementation details by using the [Guice](http://code.google.com/p/google-guice/)
which is a dependency injection framework from *Google* that allows for code modularity.

## Hello Network
There are three basic building blocks in this network API: `Network` objects, `Node` objects and `Link` objects.

To create `Network` instances we must first initialise our [Guice](http://code.google.com/p/google-guice/) injector. 
This entity is resposible for the construction of our library dependency graph. 

```java
Injector injector = Guice.createInjector(new NetworkModule());
Network network = injector.getInstance(Network.class);
```

We create an `Injector` instance from a `NetworkModule`. This module serves as a *Guice* configuration, telling 
it how to resolve the various dependencies in the network library.

To **create a network** we simply ask our injector for a instance of the `Network.class`. The injector 
will do the rest and supply an instance from an implementation of `Network` ready to be used.

The rest is pretty straight forward. The creation of new `Node` and `Link` objects is handled by the `Network`. 
Bellow you can see a simple example of how one can create new nodes and links and add them to an existing network 
instance.

```java
//create new nodes
Node node1 = network.createNode();
Node node2 = network.createNode();

//create a new link 
Link link = network.createLink();

//add nodes to the network
network.addNode(node1);
network.addNode(node2);

//add a new link from node1 to node2
network.addLink(node1,node2, link);
```

Thats pretty much it. You can now add more nodes, return links between existing nodes, get the neighbours of a 
given node, etc.

## Latest releases
### Nightly builds
Current version is 0.0.1-SNAPSHOT
* [bhave.network-0.0.1-SNAPSHOT.jar](https://mega.co.nz/#!qZRkASrL!JsvmaATj_WNjQlvJT5UL6VTMr4t4hyCjIAqAQEL39CQ)
* [bhave.network-0.0.1-SNAPSHOT-sources.jar](https://mega.co.nz/#!mQBnmCBb!NREfQR7ZEzLoxNLrmfpli1YoW24zybW1_WcM0Khqoc8)
* [bhave.network-0.0.1-SNAPSHOT-javadoc.jar](https://mega.co.nz/#!eM5jyLqD!DTJJKGY_yvsD43BDJza9ZFd8OdtVXHADe-n-UX9xnsc)

## Licence
 B-Have Network Library
 
 * Copyright (C) 2013 Davide Nunes 
 * Authors : Davide Nunes <davex.pt@gmail.com>
 * Website : http://bhaveproject.org
 
 The b-have network library is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 
 The b-have network library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with the b-have network library.  
 If not, see [GPL 3.0](http://www.gnu.org/licenses/gpl.html).
 
