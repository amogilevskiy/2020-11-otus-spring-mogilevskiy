import React from 'react';
import {Space, Table} from 'antd';
import Genre from "../../domain/Genre";
import {PaginatedList} from "../../services/PaginatedList";


interface GenreListProps {
    genres: PaginatedList<Genre>;
    loading: boolean;
    onEdit: (genre: Genre) => void;
    onPageChange: (page: number) => void;
}

const GenreList: React.FC<GenreListProps> = ({genres, loading, onEdit, onPageChange}) => {

    const columns = [
        {
            title: 'Id',
            dataIndex: 'id',
        },
        {
            title: 'Title',
            dataIndex: 'title',
        },
        {
            title: '',
            key: 'options',
            render: (text: any, record: any) => (
                <Space size="middle">
                    <a onClick={() => onEdit(record)}>Edit</a>
                </Space>
            ),
        },
    ];

    return <Table dataSource={genres.content}
                  pagination={{
                      total: genres.totalElements,
                      pageSize: genres.size,
                      current: genres.number + 1,
                      onChange: onPageChange,
                  }}
                  loading={loading}
                  columns={columns}
                  rowKey="id"/>;
};

export default GenreList;