const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");

module.exports = {
    entry: "./src/main/resources/templates/index.js",
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
            template: "./src/main/resources/templates/index.html",
            filename: "index.html",
        }),
        new HtmlWebpackPlugin({
            template: "./src/main/resources/templates/login/loginForm.html",
            filename: "login.html",
        }),
        new HtmlWebpackPlugin({
            template: "./src/main/resources/templates/reservation/reserveForm.html",
            filename: "reserve.html",
        }),
        new HtmlWebpackPlugin({
            template: "./src/main/resources/templates/room/listRooms.html",
            filename: "rooms.html",
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
        ],
    },
};
