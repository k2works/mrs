const fs = require('fs')

fs.readdir('cypress/cucumber-json/', function (err, files) {
  if (err) throw err

  const result = []
  files.forEach(file => {
    const jsonObject = JSON.parse(fs.readFileSync(`cypress/cucumber-json/${file}`, 'utf8'))
    result.push(jsonObject[0])
  })

  if (!fs.existsSync('cypress/report')) {
    fs.mkdirSync('cypress/report', (err, folder) => {
      if (err) throw err;
      console.log(folder);
    });
    fs.mkdirSync('cypress/report/cucumber', (err, folder) => {
      if (err) throw err;
      console.log(folder);
    });
  }

  fs.writeFileSync('cypress/report/cucumber/cucumber.json', JSON.stringify(result))
});
