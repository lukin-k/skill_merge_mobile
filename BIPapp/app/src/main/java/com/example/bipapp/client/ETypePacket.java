package com.example.bipapp.client;


public enum ETypePacket {
    SIGN_IN,
    SIGN_UP,

    GET_ALL_SKILLS,
    GET_USER_INFO,
    CHANGE_USER_INFO,

    GET_ALL_PROJECT_TAGS,
    CREATE_PROJECT,
    SEARCH_PROJECTS,

    SUBSCRIBE_PROJECT,
    UNSUBSCRIBE_PROJECT,
    ACCEPT_VOLUNTEER,
    LEAVE_PROJECT,

    TMP,
    BAD;
}
