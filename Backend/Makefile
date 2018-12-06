JFLAGS = -d
JC = javac

build:
	protoc -I=. --java_out=. ./proto/player.proto
	protoc -I=. --java_out=. ./proto/character.proto 
	export CLASSPATH=./compiler/protobuf-java-3.6.1.jar:$CLASSPATH
	$(JC) $(JFLAGS) . *.java
	
run:
	java Main

clean:
	$(RM) *.class && rm -rf ./packet && rm -rf ./pacman