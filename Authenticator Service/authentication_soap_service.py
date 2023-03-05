# python -m pip install lxml spyne
from spyne import Application, rpc, ServiceBase, Integer, Double, Unicode, Iterable, AnyXml
from spyne.protocol.soap import Soap11
from spyne.server.wsgi import WsgiApplication
from lxml import etree

from token_generation import generate_jwt_token, validate_jwt_token
from user_repository import get_user_by_username, get_users

class AuthService(ServiceBase):

    @rpc(Unicode, Unicode, _returns=Unicode)
    def authenticate(self, username, password):
        # Look up the user in the database
        if user is None:
            raise ValueError("Invalid username or password")

        # Check the password
        if user.password != password:
            raise ValueError("Invalid username or password")

        # Generate a JWT token for the user
        token = generate_jwt_token(user)
        return token

    @rpc(Unicode, _returns=AnyXml)
    def validate_token(self, token):

        try:
            [is_valid, decoded_token] = validate_jwt_token(token)

            if is_valid:
                # Create the response as an XML object
                response = etree.Element("response")
                valid_tag = etree.SubElement(response, "valid")
                valid_tag.text = "True"
                id_tag = etree.SubElement(response, "id")
                id_tag.text = str(decoded_token["sub"])
                role_tag = etree.SubElement(response, "role")
                role_tag.text = str(decoded_token["role"])
                return etree.tostring(response)
        except:
            pass

        response = etree.Element("response")
        valid_tag = etree.SubElement(response, "valid")
        valid_tag.text = "False"
        return etree.tostring(response)

application = Application([AuthService], 'services.auth.soap',
                          in_protocol=Soap11(validator='lxml'),
                          out_protocol=Soap11())

wsgi_application = WsgiApplication(application)

if __name__ == '__main__':
    import logging

    from wsgiref.simple_server import make_server

    # print("Creating user:")
    # new_user = create_user("test", "test")
    # print(new_user)

    print("\nUsers:")
    for user in get_users():
        print(f"{user.id} - {user.username} - {user.password} - {user.role}: ", end="")
        print()

    logging.info("listening to http://127.0.0.1:8070")
    logging.info("wsdl is at: http://127.0.0.1:8070/?wsdl")

    server = make_server('127.0.0.1', 8070, wsgi_application)
    server.serve_forever()
