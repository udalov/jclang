int i;
char ch;
char* str;
void* ptr;

typedef struct UndefinedStructImpl UndefinedStruct;

typedef struct {
    int j;
    char** p_str;
} Struct;

int int_arr[100];


void foo();
void bar(int arg);
int baz(Struct by_value, Struct* by_reference);
void* quux(Struct, Struct*, UndefinedStruct, UndefinedStruct*);

typedef int (Foo)();
typedef char* (Bar)(int k, int*);
