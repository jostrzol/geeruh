package db.changes

databaseChangeLog {
  changeSet(id: '''1673098908879-3''', author: '''iaPC (generated)''') {
    addColumn(tableName: '''projects''') {
      column(name: '''last_issue_index''', type: '''int4''')
    }
  }

  changeSet(id: '''1673098908879-4''', author: '''iaPC (generated)''') {
    addColumn(tableName: '''projects_aud''') {
      column(name: '''last_issue_index''', type: '''int4''')
    }
  }

  changeSet(id: '''1673098908879-5''', author: '''iaPC (generated)''') {
    dropSequence(sequenceName: '''issue_index_seq''')
  }
}
