import React from 'react';
import { Translate, translate, ValidatedField } from 'react-jhipster';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Alert, Row, Col, Form } from 'reactstrap';
import { Link } from 'react-router-dom';
import { type FieldError, useForm } from 'react-hook-form';

export interface ILoginModalProps {
  showModal: boolean;
  loginError: boolean;
  handleLogin: (username: string, password: string, rememberMe: boolean) => void;
  handleClose: () => void;
}

const LoginModal = (props: ILoginModalProps) => {
  const login = ({ username, password, rememberMe }) => {
    props.handleLogin(username, password, rememberMe);
  };

  const {
    handleSubmit,
    register,
    formState: { errors, touchedFields },
  } = useForm({ mode: 'onTouched' });

  const { loginError, handleClose } = props;

  const handleLoginSubmit = e => {
    handleSubmit(login)(e);
  };

  return (
    <Modal isOpen={props.showModal} toggle={handleClose} backdrop="static" id="login-page" autoFocus={false}>
      <Form onSubmit={handleLoginSubmit}>
        {/* <ModalHeader id="login-title" data-cy="loginTitle" toggle={handleClose}> */}
        {/*   <Translate contentKey="login.title">Sign in</Translate> */}
        {/* </ModalHeader> */}
        <ModalBody>
          <Row>
            <img src="content/images/yamco-logo.jpg" alt="Logo" />
            <Col md="12">
              {loginError ? (
                <Alert color="danger" data-cy="loginError">
                  <Translate contentKey="login.messages.error.authentication">
                    <strong>Failed to sign in!</strong> Please check your credentials and try again.
                  </Translate>
                </Alert>
              ) : null}
            </Col>
            <Col md="12">
              <ValidatedField
                name="username"
                label={translate('global.form.username.label')}
                placeholder={translate('global.form.username.placeholder')}
                required
                autoFocus
                data-cy="username"
                validate={{ required: 'Username cannot be empty!' }}
                register={register}
                error={errors.username as FieldError}
                isTouched={touchedFields.username}
              />
              <ValidatedField
                name="password"
                type="password"
                label={translate('login.form.password')}
                placeholder={translate('login.form.password.placeholder')}
                required
                data-cy="password"
                validate={{ required: 'Password cannot be empty!' }}
                register={register}
                error={errors.password as FieldError}
                isTouched={touchedFields.password}
              />
              <div className="row mar0">
                <ValidatedField
                  name="rememberMe"
                  type="checkbox"
                  className="col-lg-6"
                  check
                  label={translate('login.form.rememberme')}
                  value={true}
                  register={register}
                />
                <Link to="/account/reset/request" data-cy="forgetYourPasswordSelector" className="col-lg-6">
                  <Translate contentKey="login.password.forgot">Did you forget your password?</Translate>
                </Link>
              </div>
            </Col>
          </Row>
          <div className="mt-1">&nbsp;</div>
          {/* <Alert className="alert alert-primary"> */}
          {/*   <Link to="/account/reset/request" data-cy="forgetYourPasswordSelector"> */}
          {/*     <Translate contentKey="login.password.forgot">Did you forget your password?</Translate> */}
          {/*   </Link> */}
          {/* </Alert> */}

          <Alert className="alert alert-primary">
            <span>
              <Translate contentKey="global.messages.info.register.noaccount">You don&apos;t have an account yet?</Translate>
            </span>{' '}
            <Link to="/account/register">
              <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
            </Link>
          </Alert>
        </ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={handleClose} tabIndex={1}>
            <Translate contentKey="entity.action.cancel">Cancel</Translate>
          </Button>{' '}
          <Button color="primary" type="submit" data-cy="submit">
            <Translate contentKey="login.form.button">Sign in</Translate>
          </Button>
        </ModalFooter>
      </Form>
    </Modal>
  );
};

export default LoginModal;
