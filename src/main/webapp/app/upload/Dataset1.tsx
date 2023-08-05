import React from 'react';
const Dataset1 = () => {
  return (
    <>
      <nav>
        <div className="nav nav-tabs mb-3" id="nav-tab" role="tablist">
          <button
            className="nav-link active"
            id="nav-home-tab"
            data-bs-toggle="tab"
            data-bs-target="#nav-home"
            type="button"
            role="tab"
            aria-controls="nav-home"
            aria-selected="true"
          >
            Home
          </button>
          <button
            className="nav-link"
            id="nav-profile-tab"
            data-bs-toggle="tab"
            data-bs-target="#nav-profile"
            type="button"
            role="tab"
            aria-controls="nav-profile"
            aria-selected="false"
          >
            Profile
          </button>
          <button
            className="nav-link"
            id="nav-contact-tab"
            data-bs-toggle="tab"
            data-bs-target="#nav-contact"
            type="button"
            role="tab"
            aria-controls="nav-contact"
            aria-selected="false"
          >
            Contact
          </button>
        </div>
      </nav>
      <div className="tab-content p-3 border bg-light" id="nav-tabContent">
        <div className="tab-pane fade active show" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
          <p>
            <strong>This is some placeholder content the Home tabs associated content.</strong>
            Clicking another tab will toggle the visibility of this one for the next. The tab JavaScript swaps classNamees to control the
            content visibility and styling. You can use it with tabs, pills, and any other <code>.nav</code>-powered navigation.
          </p>
        </div>
        <div className="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
          <p>
            <strong>This is some placeholder content the Profile tabs associated content.</strong>
            Clicking another tab will toggle the visibility of this one for the next. The tab JavaScript swaps classNamees to control the
            content visibility and styling. You can use it with tabs, pills, and any other <code>.nav</code>-powered navigation.
          </p>
        </div>
        <div className="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
          <p>
            <strong>This is some placeholder content the Contact tabs associated content.</strong>
            Clicking another tab will toggle the visibility of this one for the next. The tab JavaScript swaps classNamees to control the
            content visibility and styling. You can use it with tabs, pills, and any other <code>.nav</code>-powered navigation.
          </p>
        </div>
      </div>
    </>
  );
};
export default Dataset1;
