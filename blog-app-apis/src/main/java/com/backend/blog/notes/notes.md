java bean is validated with JSR 300 known as Bean Validation 2.0
JSR 380 is specification for the java API for bean validation.
Properties of the bean meet the specific criteria
For Validation different annotation is used like @NotNull,@Min etc
Hibernate Validator is the implementation of JSR 380(bean vaidation API)
where as JSR 380 is the specifications
--------------------------------------------------------
Important JPA Theory:
when we use @OneToMany relationship an new table between two enities is created
so if we dont want that table to be created then we can use mappedBy annotation
suppose for example:
@Entity
class Question{
	@id
	int question_id;
	String question;
	@OneToMany(mappedBy="question")
	List<Answer> ans=new Answer<>();
}

@Entity
class Answer{
	@id
	int answer_Id;
	String answer;
	@ManyToOne
	private Question question;
}
and the foreignkey constraint will be created in Answer Table(OWEING I.E which contains the foreignkey) which will be taking
referance from primary key of Question table(NON OWING).

fetch types:
There are two fetch type:
i.Lazy
ii.Eager
Lazy:when we call getter and setters at that time only data will be loaded
for example:when we try to fetch a question and call the getAnswer() method at that time only
data will be loaded.
Eager:Data will be loaded at the spot like when we will load the Question it will load all the
answers
By default it will be Lazy
Cascading:
if there is a main entity say Question and there is another entity Answer which is a sub part
of the main entity.Now if we are saving the question then their ans won't be saved automatically
we need to mannualy save those questions and answers so inorder to overcome this problem we can use
cascade types so the if we are performing some actions on the parent Entity it will be
reflected in the child entities.

Entity relationships often depend on the existence of another entity,
for example the Person–Address relationship. Without the Person, the Address entity doesn't 
have any meaning of its own.
When we delete the Person entity, our Address entity should alsoget deleted.
Cascading is the way to achieve this. When we perfpplied to the associated entity.
mappedBy:orm some action on the target entity,
the same action will be a
public abstract java.lang.String mappedBy
The field that owns the relationship. Required unless the relationship is unidirectional.
Default:
""
@ManyToMany:
