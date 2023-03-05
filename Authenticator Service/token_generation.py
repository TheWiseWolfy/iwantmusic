import base64
import hashlib
import hmac

import jwt
import datetime
import uuid

from user_orm import User

with open('secret.key', 'rb') as f:
    secret = f.read()

def generate_jwt_token(user: User):
    # Set the token expiration time to one hour from now
    expiration_time = datetime.datetime.utcnow() + datetime.timedelta(hours=1)

    uuid_str = str(uuid.uuid4())
    # Set the payload of the token to be the user's ID and expiration time
    payload = {
        'iss': "http://localhost:8070/?wsdl",
        'sub': user.id,
        'exp': expiration_time,
        'jti': uuid_str,
        'role': user.role
    }

    # Sign the token using the secret key
    token = jwt.encode(payload, secret, algorithm='HS256')
    return token


def validate_jwt_token(token: str):

    try:
        decoded_token = jwt.decode(token, secret, algorithms='HS256', verify=True)
    except Exception as e:
        print(e)
        return False, None
    else:
        return True, decoded_token
