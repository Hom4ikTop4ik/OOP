javac src/main/java/ru/nsu/martynov/*.java -d ./build
javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages ru.nsu.martynov
java -cp ./build ru.nsu.martynov.Game