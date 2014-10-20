http-fluent-react
=================

# Isomorphic Javascript on Java platform

Isomorphic Javascript is a concept where you use the same code for rendering the UI server side or client side. It can be a great solution for keeping great SEO or great perf when the page is displayed for the first page.

## Techno for his proof of concept 

Java 8, [Nashorn](http://www.oracle.com/technetwork/articles/java/jf14-nashorn-2126515.html), [fluent-http](https://github.com/CodeStory/fluent-http) and mainly [React.JS](http://facebook.github.io/react/)

## How to run ?

First a bit of boring stuff (Maven plugin todo ?)
Install [React tools](https://www.npmjs.org/package/react-tools)
then in your dir, enter this command: jsx -x src/main/resources/app/react/ src/main/resources/app/react/compiled/

Simply run as the ReactServer class and go to http://localhost:8080/

## This is only a proof of concept :(((

Yes sadly. So I hope you will make proposal and moreover make pull-request ;)
Next we could implement this on Spring Boot for example !
