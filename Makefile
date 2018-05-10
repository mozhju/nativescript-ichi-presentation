all: lib

java_build:
	cd IChiPresentation && gradle build

lib: java_build
	cp IChiPresentation/library/build/outputs/aar/library-release.aar platforms/android/libs/IChiPresentation.aar
