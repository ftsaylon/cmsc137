JFLAGS = -d
JC = javac

build:
	protoc -I=. --java_out=. ./proto/player.proto
	protoc -I=. --java_out=. ./proto/tcp_packet.proto 
	export CLASSPATH=./compiler/protobuf-java-3.6.1.jar:$CLASSPATH
	$(JC) $(JFLAGS) . *.java
	$(JC) $(JFLAGS) ./src *.java
	
run:
	java Main

clean:
	$(RM) *.class && rm -rf ./packet && $(RM) ./src/*.class