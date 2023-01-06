package db.changes

databaseChangeLog {
  changeSet(id: '''1672916108337-1''', author: '''tuco (generated)''') {
    createSequence(incrementBy: 1, sequenceName: '''hibernate_sequence''', startValue: 1)
  }

  changeSet(id: '''1672916108337-2''', author: '''tuco (generated)''') {
    createSequence(incrementBy: 50, sequenceName: '''issue_index_seq''', startValue: 1)
  }

  changeSet(id: '''1672916108337-3''', author: '''tuco (generated)''') {
    createTable(tableName: '''comments''') {
      column(name: '''comment_id''', type: '''VARCHAR(255)''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''commentsPK''')
      }
      column(name: '''content''', type: '''OID''')
      column(name: '''created_at''', type: '''TIMESTAMP WITHOUT TIME ZONE''')
      column(name: '''modified_at''', type: '''TIMESTAMP WITHOUT TIME ZONE''')
      column(name: '''creator_user_id''', type: '''VARCHAR(255)''')
      column(name: '''issue_issue_index''', type: '''INTEGER''')
      column(name: '''issue_project_code''', type: '''VARCHAR(255)''')
    }
  }

  changeSet(id: '''1672916108337-4''', author: '''tuco (generated)''') {
    createTable(tableName: '''issues''') {
      column(name: '''issue_index''', type: '''INTEGER''', autoIncrement: true) {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''issuesPK''')
      }
      column(name: '''project_code''', type: '''VARCHAR(255)''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''issuesPK''')
      }
      column(name: '''description''', type: '''OID''')
      column(name: '''summary''', type: '''VARCHAR(120)''')
      column(name: '''type''', type: '''VARCHAR(255)''')
      column(name: '''user_id''', type: '''VARCHAR(255)''')
      column(name: '''status_code''', type: '''VARCHAR(255)''')
    }
  }

  changeSet(id: '''1672916108337-5''', author: '''tuco (generated)''') {
    createTable(tableName: '''issues_aud''') {
      column(name: '''issue_index''', type: '''INTEGER''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''issues_audPK''')
      }
      column(name: '''project_code''', type: '''VARCHAR(255)''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''issues_audPK''')
      }
      column(name: '''rev''', type: '''INTEGER''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''issues_audPK''')
      }
      column(name: '''revtype''', type: '''SMALLINT''')
      column(name: '''description''', type: '''OID''')
      column(name: '''summary''', type: '''VARCHAR(120)''')
      column(name: '''type''', type: '''VARCHAR(255)''')
      column(name: '''user_id''', type: '''VARCHAR(255)''')
      column(name: '''status_code''', type: '''VARCHAR(255)''')
    }
  }

  changeSet(id: '''1672916108337-6''', author: '''tuco (generated)''') {
    createTable(tableName: '''projects''') {
      column(name: '''code''', type: '''VARCHAR(255)''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''projectsPK''')
      }
      column(name: '''description''', type: '''VARCHAR(255)''')
      column(name: '''name''', type: '''VARCHAR(255)''')
    }
  }

  changeSet(id: '''1672916108337-7''', author: '''tuco (generated)''') {
    createTable(tableName: '''projects_aud''') {
      column(name: '''code''', type: '''VARCHAR(255)''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''projects_audPK''')
      }
      column(name: '''rev''', type: '''INTEGER''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''projects_audPK''')
      }
      column(name: '''revtype''', type: '''SMALLINT''')
      column(name: '''description''', type: '''VARCHAR(255)''')
      column(name: '''name''', type: '''VARCHAR(255)''')
    }
  }

  changeSet(id: '''1672916108337-8''', author: '''tuco (generated)''') {
    createTable(tableName: '''related_issues''') {
      column(name: '''related_issue_index''', type: '''INTEGER''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''related_issuesPK''')
      }
      column(name: '''related_issue_project_code''', type: '''VARCHAR(255)''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''related_issuesPK''')
      }
      column(name: '''related_issue_index_child''', type: '''INTEGER''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''related_issuesPK''')
      }
      column(name: '''related_issue_project_code_child''', type: '''VARCHAR(255)''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''related_issuesPK''')
      }
    }
  }

  changeSet(id: '''1672916108337-9''', author: '''tuco (generated)''') {
    createTable(tableName: '''revinfo''') {
      column(name: '''rev''', type: '''INTEGER''', autoIncrement: true) {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''revinfoPK''')
      }
      column(name: '''revtstmp''', type: '''BIGINT''')
    }
  }

  changeSet(id: '''1672916108337-10''', author: '''tuco (generated)''') {
    createTable(tableName: '''statuses''') {
      column(name: '''code''', type: '''VARCHAR(255)''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''statusesPK''')
      }
      column(name: '''name''', type: '''VARCHAR(255)''')
      column(name: '''order_number''', type: '''INTEGER''')
    }
  }

  changeSet(id: '''1672916108337-11''', author: '''tuco (generated)''') {
    createTable(tableName: '''users''') {
      column(name: '''user_id''', type: '''VARCHAR(255)''') {
        constraints(nullable: false, primaryKey: true, primaryKeyName: '''usersPK''')
      }
      column(name: '''email''', type: '''VARCHAR(255)''')
      column(name: '''first_name''', type: '''VARCHAR(255)''')
      column(name: '''login''', type: '''VARCHAR(255)''')
      column(name: '''password_hash''', type: '''VARCHAR(255)''')
      column(name: '''second_name''', type: '''VARCHAR(255)''')
      column(name: '''surname''', type: '''VARCHAR(255)''')
    }
  }

  changeSet(id: '''1672916108337-12''', author: '''tuco (generated)''') {
    addUniqueConstraint(columnNames: '''order_number''', constraintName: '''UC_STATUSESORDER_NUMBER_COL''', tableName: '''statuses''')
  }

  changeSet(id: '''1672916108337-13''', author: '''tuco (generated)''') {
    addUniqueConstraint(columnNames: '''email''', constraintName: '''UC_USERSEMAIL_COL''', tableName: '''users''')
  }

  changeSet(id: '''1672916108337-14''', author: '''tuco (generated)''') {
    addUniqueConstraint(columnNames: '''login''', constraintName: '''UC_USERSLOGIN_COL''', tableName: '''users''')
  }

  changeSet(id: '''1672916108337-15''', author: '''tuco (generated)''') {
    addForeignKeyConstraint(baseColumnNames: '''issue_issue_index,issue_project_code''', baseTableName: '''comments''', constraintName: '''FK1pwp8mh4ebr8hi4gp23w03ymf''', deferrable: false, initiallyDeferred: false, referencedColumnNames: '''issue_index,project_code''', referencedTableName: '''issues''', validate: true)
  }

  changeSet(id: '''1672916108337-16''', author: '''tuco (generated)''') {
    addForeignKeyConstraint(baseColumnNames: '''related_issue_index_child,related_issue_project_code_child''', baseTableName: '''related_issues''', constraintName: '''FK4a1fhgqdpd846vem4hxerbn03''', deferrable: false, initiallyDeferred: false, referencedColumnNames: '''issue_index,project_code''', referencedTableName: '''issues''', validate: true)
  }

  changeSet(id: '''1672916108337-17''', author: '''tuco (generated)''') {
    addForeignKeyConstraint(baseColumnNames: '''status_code''', baseTableName: '''issues''', constraintName: '''FKcf1pgmtdoxuhuu85tx3t0prpe''', deferrable: false, initiallyDeferred: false, referencedColumnNames: '''code''', referencedTableName: '''statuses''', validate: true)
  }

  changeSet(id: '''1672916108337-18''', author: '''tuco (generated)''') {
    addForeignKeyConstraint(baseColumnNames: '''user_id''', baseTableName: '''issues''', constraintName: '''FKcigc16s3flsg53i2sy0m37e''', deferrable: false, initiallyDeferred: false, referencedColumnNames: '''user_id''', referencedTableName: '''users''', validate: true)
  }

  changeSet(id: '''1672916108337-19''', author: '''tuco (generated)''') {
    addForeignKeyConstraint(baseColumnNames: '''project_code''', baseTableName: '''issues''', constraintName: '''FKcv27vu2ahrg6byj8aypcur9m5''', deferrable: false, initiallyDeferred: false, referencedColumnNames: '''code''', referencedTableName: '''projects''', validate: true)
  }

  changeSet(id: '''1672916108337-20''', author: '''tuco (generated)''') {
    addForeignKeyConstraint(baseColumnNames: '''related_issue_index,related_issue_project_code''', baseTableName: '''related_issues''', constraintName: '''FKg62c2ov1a23fj310qlb2b6xs7''', deferrable: false, initiallyDeferred: false, referencedColumnNames: '''issue_index,project_code''', referencedTableName: '''issues''', validate: true)
  }

  changeSet(id: '''1672916108337-21''', author: '''tuco (generated)''') {
    addForeignKeyConstraint(baseColumnNames: '''creator_user_id''', baseTableName: '''comments''', constraintName: '''FKilxehsm146qh37rooruem6xfj''', deferrable: false, initiallyDeferred: false, referencedColumnNames: '''user_id''', referencedTableName: '''users''', validate: true)
  }

  changeSet(id: '''1672916108337-22''', author: '''tuco (generated)''') {
    addForeignKeyConstraint(baseColumnNames: '''rev''', baseTableName: '''projects_aud''', constraintName: '''FKmj8bve3ipxgp5iw5j3ombgx6x''', deferrable: false, initiallyDeferred: false, referencedColumnNames: '''rev''', referencedTableName: '''revinfo''', validate: true)
  }

  changeSet(id: '''1672916108337-23''', author: '''tuco (generated)''') {
    addForeignKeyConstraint(baseColumnNames: '''rev''', baseTableName: '''issues_aud''', constraintName: '''FKqr1am7x28smkh703kihx2fvs4''', deferrable: false, initiallyDeferred: false, referencedColumnNames: '''rev''', referencedTableName: '''revinfo''', validate: true)
  }

}
