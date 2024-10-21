# Compiler and flags
CXX = g++
CXXFLAGS = -std=c++11

# JNI include directories
JAVA_HOME = /usr/lib/jvm/java-21-openjdk-amd64# or manually set JAVA_HOME if not defined in the environment
INCLUDES = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux  # For macOS, use /include/darwin, for Windows use /include/win32

# MySQL Connector include path and library
MYSQL_INCLUDES = -I/usr/include/mysql  # Adjust based on your installation
MYSQL_LIBS = -lmysqlcppconn

# Source and output
SRCDIR = src
OBJDIR = obj
LIBDIR = lib
SOURCES = $(SRCDIR)/RoomManagement.cpp $(SRCDIR)/RoomManagementJNI.cpp
OBJECTS = $(patsubst $(SRCDIR)/%.cpp,$(OBJDIR)/%.o,$(SOURCES))
LIBRARY = $(LIBDIR)/libRoomManagementJNI.so  # For Linux/macOS; use .dll for Windows

# Commands
all: $(LIBRARY)

# Rule for creating object files
$(OBJDIR)/%.o: $(SRCDIR)/%.cpp
	mkdir -p $(OBJDIR)
	$(CXX) $(CXXFLAGS) $(INCLUDES) $(MYSQL_INCLUDES) -fPIC -c $< -o $@

# Rule for creating shared library
$(LIBRARY): $(OBJECTS)
	mkdir -p $(LIBDIR)
	$(CXX) -shared -o $(LIBRARY) $(OBJECTS) $(MYSQL_LIBS)

# Clean rule to remove object files and library
clean:
	rm -rf $(OBJDIR) $(LIBDIR)
