# Implementation Progress

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Gradle](https://img.shields.io/badge/gradle-v6.3-blue)](https://img.shields.io/badge/gradle-v6.3-blue)
[![Build Status](https://travis-ci.org/BranislavBeno/Project-Implementation-Progress.svg?branch=master)](https://travis-ci.org/BranislavBeno/Project-Implementation-Progress)

Abstract
========
This README file describes the console application ImplProgress,
which is used for automated implementation progress gathering from issue tracker tool.
Collected results are according to tool settings sent to Excel, CSV or HTML file.
Precondition for automated data gathering is, that issue tracker tool
allows communication over REST API.


Table of Contents
=================
1. Installation
2. Usage
3. Known issues


Installation
===============
No installation is required. Just copy file "ImplProgress.jar" into requested
directory.


Usage
========
All info, warning and error messages are issued to standard output stream
and to log file as well.

Following command line parameters are available for application usage:

Option                          Description:
---------------------------------------------
-u  | --user [username]         - defines user name for connection to issue
                                  tracker tool

-p  | --password [password]     - defines password for connection to issue
                                  tracker tool


Possible combinations of parameters:
------------------------------------
-u [username] -p [password]


Known issues
===============
No issues are known at this time.
