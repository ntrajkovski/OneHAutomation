Feature: Publish documents

  @highprio @publish @smoke
  Scenario: Accept a request to publish a new document (Editor)
    Given publish request existed
    #Do it with a second browser
    #Check if I can do it with API
    And editor was logged in
    When the user accepts the request
    Then the status of the document is live
    And  the document appears on the website

  @highprio @publish
  Scenario: Accept a request to publish changed document
    Given publish request for a changed document existed
    And editor was logged in
    When the user accepts the request
    Then the status of the document is live
    And the changed document appears on the website

  @smoke @highprio @publish
  Scenario: Reject to publish a new document
    Given publish request existed
    And editor was logged in
    When the user rejects the request
    #Then the document does not appear on the website
    Then the author receives a notification that the request was rejected

  @mediumprio @publish
  Scenario: Reject a request to publish changed document
    Given publish request for a changed document existed
    And editor was logged in
    When the user rejects the request
    Then the document remains the same on the website
    And the author receives a notification that the request was rejected

  @mediumprio @publish
  Scenario: Accept a request to publish a new scheduled document
    Given scheduled publish request existed
    And editor was logged in
    When the user accepts the request
    Then the document appears on the website only when it is scheduled

  @undefined
  Scenario: Reject a request to publish a document with a message
    Given editor was logged in
    And publish request existed for a changed document
    When the user rejects the request with a message
    Then the author receives the correct message for the rejected document
  @undefined
  Scenario: Drop request

  @undefined
  Scenario: See the changes between the old and the changed document
    Given editor was logged in
    And publish request existed for a changed document
    When the logged-in user opens the changed document
    Then the document change editor displays the correct changes
  @undefined
  Scenario: Editor publishes a new document
    Given editor was logged in
    When the logged in user creates a new document
    And the user publishes the document
    Then the document state changed to live
    And the document appears on the website
  @undefined
  Scenario: Editor publishes a changed document
    Given editor was logged in
    And published document existed
    When the logged in user changes the published document
    And the user publishes the document
    Then the document state changed to live
    And the document appears on the website
  @undefined
  Scenario: Document in the offline state cannot be taken offline
    Given author was logged in
    When the logged in user creates a new document
    Then the logged in user cannot take offline the document
  @undefined
  Scenario: Document in the offline state cannot be scheduled to be taken offline
    Given author was logged in
    When the logged in user creates a new document
    Then the logged in user cannot schedule to take offline the document

  @undefined
  Scenario: Cancel request
    Given author was logged in
    And a request for a new document existed
    When the logged in user cancels the request
    Then the state is unpublished changes
    And the request disappeared from the editor's pending requests
  @undefined
  Scenario: Author cannot schedule a document in the past
    Given author was logged in
    When the logged in user creates a new document
    And schedules the document to be published in the past
    Then an error appears that the scheduled time is in the past
  @undefined
  Scenario: Unpubished changes when the document is saved, but not published