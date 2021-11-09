#!/usr/bin/env python
# -*- coding: utf-8 -*-
# by vellhe 2017/7/9
from flask import Flask
from flask_restful import reqparse, abort, Api, Resource

app = Flask(__name__)
api = Api(app)


from SearchFileList import SearchList
'''
TODOS = {
    'todo1': {'task': 'build an API'},
    'todo2': {'task': '哈哈哈'},
    'todo3': {'task': 'profit!'},
}
'''

def abort_if_todo_doesnt_exist(todo_id):
    if todo_id not in TODOS:
        abort(404, message="Todo {} doesn't exist".format(todo_id))



'''
# Todo
# shows a single todo item and lets you delete a todo item
class Todo(Resource):
    def get(self, todo_id):
        abort_if_todo_doesnt_exist(todo_id)
        return TODOS[todo_id]

    def delete(self, todo_id):
        abort_if_todo_doesnt_exist(todo_id)
        del TODOS[todo_id]
        return '', 204

    def post(self):
        args = parser.parse_args()
        todo_id = int(max(TODOS.keys()).lstrip('todo')) + 1
        todo_id = 'todo%i' % todo_id
        TODOS[todo_id] = {'task': args['task'], 'task1': args['task']}

        return TODOS[todo_id], 201

    def put(self, todo_id):
        args = parser.parse_args()
        task = {'task': args['task']}
        TODOS[todo_id] = task
        return task, 201


# TodoList
# shows a list of all todos, and lets you POST to add new tasks
class TodoList(Resource):
    def get(self):
        return TODOS

    def post(self):
        args = parser.parse_args()
        todo_id = int(max(TODOS.keys()).lstrip('todo')) + 1
        todo_id = 'todo%i' % todo_id
        TODOS[todo_id] = {'tas222k': args['data'], 'task1': args['data']}

        return TODOS[todo_id], 201

'''

class TodoCopmpare(Resource):
    # def get(self):
    #    return TODOS

    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('keyword')
        parser.add_argument('searchpath')
        parser.add_argument('FullBook')

        args = parser.parse_args()

        keyWord =  args['keyword']
        searchpath = args['searchpath']
        page = args['FullBook']

        keyWordpath = SearchList(searchpath,keyWord,page)
#       argsData.add_argument('imgUrl')

        # rserargsData = argsData.argsData()

        returnJson =  keyWordpath
        return returnJson, 200


##
# # Actually setup the Api resource routing here
##
api.add_resource(TodoCopmpare, '/TodoCopmpare')
# api.add_resource(TodoList, '/todos')
# api.add_resource(Todo, '/todos/<todo_id>')

if __name__ == '__main__':
    app.run(debug=True)
