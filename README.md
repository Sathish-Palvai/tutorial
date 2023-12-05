# Three layer design

1.  Layer1: Presentation      (Controller)
    Controller manages the Model and presents the view

2.  Layer2: Business Logic    (Service)
    Service class contains business related operations. Service is also the middleman between Controller and Repository
    
3.  Layer3: Data Access       (Repository)
    Respository makes CRUD operations on the datastore

# Hibernate Relation Ships

1. Unidirectionaln Relationship
   Many rows in the child table belong to One row in the parent table

   The student primary key column identifies each student
   The grade primary key column identifies each grade
   The grade foreign key column references the student primary key column