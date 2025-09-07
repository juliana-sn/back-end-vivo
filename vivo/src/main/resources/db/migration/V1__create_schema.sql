CREATE TABLE teams (
    id_team SERIAL,
    name VARCHAR(100),
    department VARCHAR(50),
    CONSTRAINT team_id_pk PRIMARY KEY (id_team),
    CONSTRAINT teams_name_un UNIQUE (name),
    CONSTRAINT teams_name_nn CHECK (name IS NOT NULL),
    CONSTRAINT teams_department_nn CHECK (department IS NOT NULL)
);

CREATE TABLE platforms (
    id_platform SERIAL,
    name VARCHAR(100),
    type_access VARCHAR(50),
    url VARCHAR(200),
    CONSTRAINT platform_id_pk PRIMARY KEY (id_platform),
    CONSTRAINT platform_name_un UNIQUE (name),
    CONSTRAINT platform_name_nn CHECK (name IS NOT NULL),
    CONSTRAINT platform_type_access_nn CHECK (type_access IS NOT NULL),
    CONSTRAINT platform_url_nn CHECK (url IS NOT NULL)
);

CREATE TABLE teams_platforms (
    id_team INTEGER,
    id_platform INTEGER,
    CONSTRAINT teams_platforms_pk PRIMARY KEY (id_team, id_platform),
    CONSTRAINT team_id_fk FOREIGN KEY (id_team) REFERENCES teams(id_team),
    CONSTRAINT platform_id_fk FOREIGN KEY (id_platform) REFERENCES platforms(id_platform)
);

CREATE TABLE users (
    id_user SERIAL,
    name VARCHAR(25),
    last_name VARCHAR(50),
    position VARCHAR(50),
    role VARCHAR(12),
    email VARCHAR(255),
    password VARCHAR(255),
    telephone VARCHAR(20),
    id_team INTEGER,
    CONSTRAINT users_id_pk PRIMARY KEY (id_user),
    CONSTRAINT users_name_nn CHECK (name IS NOT NULL),
    CONSTRAINT users_last_name_nn CHECK (last_name IS NOT NULL),
    CONSTRAINT users_position_nn CHECK (position IS NOT NULL),
    CONSTRAINT users_role_nn CHECK (role IS NOT NULL),
    CONSTRAINT users_email_nn CHECK (email IS NOT NULL),
    CONSTRAINT users_email_un UNIQUE (email),
    CONSTRAINT users_password_nn CHECK (password IS NOT NULL),
    CONSTRAINT users_id_team_nn CHECK (id_team IS NOT NULL),
    CONSTRAINT users_team_fk FOREIGN KEY (id_team) REFERENCES teams(id_team)
);

CREATE TABLE onboardings (
    id_onboarding SERIAL,
    active BOOLEAN,
    dt_begin DATE,
    dt_end DATE,
    CONSTRAINT onboardings_id_pk PRIMARY KEY (id_onboarding),
    CONSTRAINT onboardings_active_nn CHECK (active IS NOT NULL),
    CONSTRAINT onboardings_dt_begin_nn CHECK (dt_begin IS NOT NULL),
    CONSTRAINT onboardings_dt_end_nn CHECK (dt_end IS NOT NULL),
    CONSTRAINT onboardings_dt_end_check CHECK (dt_end > dt_begin)
);

CREATE TABLE reports (
    id_report SERIAL,
    feeling INTEGER,
    created_at TIMESTAMP,
    report_comment VARCHAR(500),
    event VARCHAR(200),
    question VARCHAR(200),
    id_user INTEGER,
    id_onboarding INTEGER,
    CONSTRAINT reports_id_pk PRIMARY KEY (id_report),
    CONSTRAINT reports_feeling_nn CHECK (feeling IS NOT NULL),
    CONSTRAINT reports_feeling_min CHECK (feeling > 0),
    CONSTRAINT reports_created_at_nn CHECK (created_at IS NOT NULL),
    CONSTRAINT reports_id_user_nn CHECK (id_user IS NOT NULL),
    CONSTRAINT reports_id_onboarding_nn CHECK (id_onboarding IS NOT NULL),
    CONSTRAINT reports_user_fk FOREIGN KEY (id_user) REFERENCES users(id_user),
    CONSTRAINT reports_onboarding_fk FOREIGN KEY (id_onboarding) REFERENCES onboardings(id_onboarding)
);

CREATE TABLE users_onboardings (
    id_user INTEGER,
    id_onboarding INTEGER,
    CONSTRAINT users_onboardings_pk PRIMARY KEY (id_user, id_onboarding),
    CONSTRAINT id_user_fk FOREIGN KEY (id_user) REFERENCES users(id_user),
    CONSTRAINT id_onboarding_fk FOREIGN KEY (id_onboarding) REFERENCES onboardings(id_onboarding)
);

CREATE TABLE steps (
    id_step SERIAL,
    name VARCHAR(100),
    description VARCHAR(300),
    step_order INTEGER,
    in_progress BOOLEAN,
    id_onboarding INTEGER,
    CONSTRAINT steps_id_pk PRIMARY KEY (id_step),
    CONSTRAINT steps_name_nn CHECK (name IS NOT NULL),
    CONSTRAINT steps_description_nn CHECK (description IS NOT NULL),
    CONSTRAINT steps_step_order_nn CHECK (step_order IS NOT NULL),
    CONSTRAINT steps_step_order_min CHECK (step_order > 0),
    CONSTRAINT steps_in_progress_nn CHECK (in_progress IS NOT NULL),
    CONSTRAINT steps_id_onboarding_nn CHECK (id_onboarding IS NOT NULL),
    CONSTRAINT steps_onboarding_fk FOREIGN KEY (id_onboarding) REFERENCES onboardings(id_onboarding)
);

CREATE TABLE tasks (
    id_task SERIAL,
    name VARCHAR(100),
    completed BOOLEAN,
    standard BOOLEAN,
    id_step INTEGER,
    CONSTRAINT tasks_id_pk PRIMARY KEY (id_task),
    CONSTRAINT tasks_name_nn CHECK (name IS NOT NULL),
    CONSTRAINT tasks_completed_nn CHECK (completed IS NOT NULL),
    CONSTRAINT tasks_id_step_nn CHECK (id_step IS NOT NULL),
    CONSTRAINT tasks_steps_fk FOREIGN KEY (id_step) REFERENCES steps(id_step)
);

CREATE TABLE chats (
    id_chat SERIAL,
    CONSTRAINT chats_id_pk PRIMARY KEY (id_chat)
);

CREATE TABLE chats_users (
    id_chat INTEGER,
    id_user INTEGER,
    CONSTRAINT chats_users_pk PRIMARY KEY (id_chat, id_user),
    CONSTRAINT chats_users_user_fk FOREIGN KEY (id_user) REFERENCES users(id_user),
    CONSTRAINT chats_users_chat_fk FOREIGN KEY (id_chat) REFERENCES chats(id_chat)
);

CREATE TABLE messages (
    id_message SERIAL,
    time TIMESTAMP WITH TIME ZONE,
    content VARCHAR(500),
    id_chat INTEGER,
    CONSTRAINT messages_id_pk PRIMARY KEY (id_message),
    CONSTRAINT messages_time_nn CHECK (time IS NOT NULL),
    CONSTRAINT messages_content_nn CHECK (content IS NOT NULL),
    CONSTRAINT messages_id_chat_nn CHECK (id_chat IS NOT NULL),
    CONSTRAINT messages_chats_fk FOREIGN KEY (id_chat) REFERENCES chats(id_chat)
);
