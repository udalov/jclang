#!/usr/bin/env python
# 
# Extracts constants from Clang's CXCursorKind enum and renames them
# according to Java name conventions.
#
# Prints almost a ready-to-use Java enum body to stdout.
# Doesn't handle the case of one enum value initialization by the other
# (e.g. AsmStmt=GCCAsmStmt)

import sys

if len(sys.argv) != 2:
    print "Usage:", sys.argv[0], "/path/to/Index.h"
    sys.exit(1)

def javanize(s):
    res = s[0]
    n = len(s)
    for i in xrange(1, n):
        c = s[i]
        if c.islower():
            res += c.upper()
        else:
            # Special case for "OBJC"
            if i >= 3 and s[i-3:i+1] == "ObjC":
                res += c
                continue
            if not s[i-1].isupper() or (i < n-1 and not s[i+1].isupper()):
                res += "_"
            res += c
    return res


index = open(sys.argv[1], "r")
inside = False
for line in index:
    if inside and line == "};":
        break
    if "enum CXCursorKind {" in line:
        inside = True
    if inside and line.strip().startswith("CXCursor_"):
        stripCXCursor = line[line.find("CXCursor_") + len("CXCursor_"):];
        cName = stripCXCursor[0:stripCXCursor.find(" ")]
        # Special case for First../Last.. cursor kinds, which are purely informative
        if cName.startswith("First"):
            continue
        if cName.startswith("Last"):
            print
            continue
        name = javanize(cName)
        print "    " + name + ","

