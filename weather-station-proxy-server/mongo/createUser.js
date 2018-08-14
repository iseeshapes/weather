db.createUser({
  user: "weatherApplication",
  pwd: "weatherPassword",
  roles: [
    { role: "readWrite", db: "weather" }
  ]
});
