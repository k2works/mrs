const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
    entry: "./src/main/resources/static/ts/index.tsx",
    output: {
        filename: "bundle.js",
        path: path.resolve(__dirname, "src/main/resources/static/js"),
    },
    devtool: "inline-source-map",
    devServer: {
        contentBase: path.join(__dirname, 'dist'),
        compress: true,
        port: 9000,
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: "./src/main/resources/templates/app.html",
            filename: "index.html",
        }),
    ],
    module: {
        rules: [
            {
                test: /\.css/,
                use: [
                    "style-loader",
                    {
                        loader: "css-loader",
                        options: { url: false },
                    },
                ],
            },
            {
                test: /\.tsx?$/,
                use: "ts-loader"
            }
        ],
    },
    resolve: {
        extensions: [".ts", ".tsx", ".js", ".json"]
    },
    target: ["web", "es5"],
};
