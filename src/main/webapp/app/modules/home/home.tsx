import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="12">
        {account?.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <>
            <div id="carouselExampleControls" className="carousel slide" data-bs-ride="carousel">
              <div className="carousel-inner">
                <div className="carousel-item active">
                  <img src="content/images/banner-img.avif" className="d-block w-100" alt="..." />
                </div>
                <div className="carousel-item">
                  <img src="content/images/banner-img2.avif" className="d-block w-100" alt="..." />
                </div>
              </div>
              <button className="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Previous</span>
              </button>
              <button className="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Next</span>
              </button>
            </div>
            {/* <div className="get_q"> */}
            {/*     <div className="container"> */}
            {/*         <div className="row"> */}
            {/*             <div className="col-md-9"> */}
            {/*                 <h3>We Care While We Build Since 1995</h3> */}
            {/*             </div> */}
            {/*             <div className="col-md-3"> */}
            {/*                 <button className="btn btn-info">GET A QUOTE</button> */}
            {/*             </div> */}
            {/*         </div> */}
            {/*     </div> */}
            {/* </div> */}
            <div className="box_div">
              <div className="container">
                <div className="row">
                  <div className="col-md-4">
                    <div className="inner_box">
                      <img src="content/images/scope-contracting3.avif" />
                      <h4>Design & Built</h4>
                      <p>
                        Good planning and implementing well-defined processes helps us deliver quality output. Our adaptive working style
                        helps collaborate with clients and deliver efficiently.
                      </p>
                    </div>
                  </div>
                  <div className="col-md-4">
                    <div className="inner_box">
                      <img src="content/images/scope-operation2.avif" />
                      <h4>Cloud Computing</h4>
                      <p>
                        YAMCO is a one-stop cloud computing solution provider for UI, backend, analysis, business intelligence, machine
                        learning and reporting.
                      </p>
                    </div>
                  </div>
                  <div className="col-md-4">
                    <div className="inner_box">
                      <img src="content/images/scope-designbuild.avif" />
                      <h4>Software Quality Assurance</h4>
                      <p>Software Quality Assurance We believe that A well-functioning software means a well-tested software</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </>
        )}
      </Col>
    </Row>
  );
};

export default Home;
