BLOG

TASK 

Create a simple application blog as the REST server, that performs following functions:

•	Registration

•	Login

•	Browse articles

•	Adding a new article

•	Editing an article

•	Delete article

•	The ability to change the status of the article (public/draft)

•	Add tags

•	Search by tags and article title

•	Adding new comments

•	The output of the list of articles and comments should be able to paginate (skip, limit) and filtering options by fields.
All users of the system, including non-registered users, should be able to view public articles.
Registration and authorization.

Registration should be according to the following scenario:

•	user enter the necessary registration data

•	the system generates a confirmation link (code), adds it to Redis and sends it to the entered email

•	Codes for email have a life of 24 hours

•	when user follow the link, /auth/confirm/:hash_code the system searches for the code in Redis and, if available, activates the user and allows him to login

•	while user has not confirmed the email, when he tries to do authorization, user get error.

Fields of user's entity:

User {

    id: Integer/UID,
    
    first_name: String,
    
    last_name: String,
    
    password: String,
    
    email: String,
    
    created_at: Date
    
}

User must have be able to recover password according to standard scenario. /auth/forgot_passwordPOST {email} system must send a letter with a code and add this code to REDIS.

/auth/reset POST {code, new_password} takes code from REDIS and if the code is correct, user can set new password.

/auth/check_code GET {code} checks if the reset code is actual and returns an answer

Articles

Fields of article's entity:

Article {

    id: Integer/UID,
    
    title: String,
    
    text: Text,
    
    status: Enum,
    
    author_id: Integer/UID
    
    created_at: Date,
    
    updated_at: Date
    
}

REST

PUT /articles/:id - Edite articles can the author of article only.

POST /articles - Add article

GET /articles - Get public articles

GET /my - Get list of articles of authorized user.

DELETE /articles/:id - Delete article. Delete article can the author of article only. 

Filtering should be like this: /articles?skip=0&limit=10&q=post_title&author=id&sort=field_name&order=asc|desc

Comments

Fields of comment's entity:

Comment {

    id: Integer/UID,
    
    message: Text,
    
    post_id: Integer/UID,
    
    author_id: Integer/UID
    
    created_at: Date
    
}

REST POST /articles/:id/comments - Add with link to post and user.

GET /articles/:id/comments - List of comments of article.

GET /articles/:id/comments/:id - Browse comments.

DELETE /articles/:id/comments/:id - Delete comment. Delete comment can the author of article of comment only.

Filtering should be like this: /articles/:id/comments?skip=0&limit=10&q=post_title&author=id&sort=field_name&order=asc|desc

Tags

Fields of tag's entity:

Tag {

    id: Integer/UID,
    
    name: String
    
    }
    
    Relaions should be "many to many", with additional table. Tags are passed as an array when creating or editing a post. If the transferred tag already exists in the database, there is no need to duplicate it, you need to add the ID of an existing post.
REST

GET /articles?tags=tag1,tag2 - in the result of searchig should be articles, which contains at least one search tag.

GET /tags-cloud - Tags Cloud. Here is necessary to count the amount of articles that have the tag. Approximate result: [{tag: 'tagName', post_count: 10}]

Testing:

You should create 2 Unit tests and 2 integration tests. 

•	
For authorization you should use standart JWT ( JSON Web Token ).

•	For check API you can use Postman.

•	Project needs to be done with Docker container.

