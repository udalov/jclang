@interface I {
    IBOutlet id a;
    IBOutletCollection(id) id b;
    IBOutlet IBOutletCollection(id) id c;
}

- (IBAction)foo;

@end
