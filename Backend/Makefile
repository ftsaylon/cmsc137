JFLAGS = -d
JC = javac

build:
	$(JC) $(JFLAGS) . *.java
	$(JC) $(JFLAGS) ./src *.java

run:
	java Main

game:
	java pacmanGame

clean:
	$(RM) *.class && rm -rf ./packet && $(RM) ./src/*.class
