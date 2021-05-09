FROM node:15.14 as builder

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

RUN npm install npm@7.9.0 -g

COPY package.json package-lock.json tsconfig.json /app/

RUN npm ci

COPY public /app/public
COPY src /app/src

RUN npm run build

FROM nginx:1.19.10-alpine

COPY --from=builder /app/build /var/www
COPY ./nginx.conf /etc/nginx/nginx.conf
CMD ["nginx", "-g", "daemon off;"]