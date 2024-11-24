# Variables
CPP_DIR := .
JAVA_INCLUDE := /usr/lib/jvm/java-21-openjdk-amd64/include
JAVA_INCLUDE_LINUX := /usr/lib/jvm/java-21-openjdk-amd64/include/linux
CXX := g++
CXXFLAGS := -shared -fPIC -I$(JAVA_INCLUDE) -I$(JAVA_INCLUDE_LINUX) -lcrypto

# Find all .cpp files in the CPP_DIR and its subdirectories
SOURCES := $(shell find $(CPP_DIR) -type f -name '*.cpp')

# Generate corresponding .so targets
TARGETS := $(SOURCES:.cpp=.so)

# Default target
all: $(TARGETS)

# Rule to compile each .cpp file to a shared library
%.so: %.cpp
	$(CXX) $< $(CXXFLAGS) -o lib$(notdir $@)

# Clean up all generated files
clean:
	find $(CPP_DIR) -type f -name '*.so' -exec rm -f {} +
