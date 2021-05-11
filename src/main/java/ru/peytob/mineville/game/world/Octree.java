package ru.peytob.mineville.game.world;

import ru.peytob.mineville.game.object.Block;
import ru.peytob.mineville.game.registry.BlockRegistry;

import ru.peytob.mineville.graphic.Mesh;

import ru.peytob.mineville.math.CoordinatesUtils;
import ru.peytob.mineville.math.Mat4;
import ru.peytob.mineville.math.Vec3i;

import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class Octree {
    private final Vec3i position;
    private final AbstractNode root;

    public Octree(Vec3i position, int rootSize) {
        this.position = position;

        root = new InnerNode(new Vec3i(0, 0, 0), rootSize);
    }

    public Octree(Vec3i position) {
        this(position, 16);
    }

    public Vec3i getPosition() {
        return position;
    }

    public int getBlocksInsideCount() {
        return root.getBlocksInsideCount();
    }

    public boolean setBlock(Vec3i _position, Block _block) {
        if (_block == null || _block.getId() == 0) {
            return deleteBlock(_position);
        }

        return root.setBlock(_position, _block);
    }

    public boolean deleteBlock(Vec3i _position) {
        return root.deleteBlock(_position);
    }

    public Block getBlock(Vec3i _position) {
        return root.getBlock(_position);
    }

    public void draw() {
        root.draw();
    }

    private abstract static class AbstractNode {
        protected final Vec3i position;
        protected final int sizes;

        public AbstractNode(Vec3i position, int sizes) {
            this.position = position;
            this.sizes = sizes;
        }

        abstract boolean setBlock(Vec3i _position, Block _block);

        abstract Block getBlock(Vec3i _position);

        abstract boolean deleteBlock(Vec3i _position);

        abstract int getBlocksInsideCount();

        abstract void draw();

        public Vec3i getPosition() {
            return position;
        }

        public int getSizes() {
            return sizes;
        }
    }

    private class InnerNode extends AbstractNode {
        private final AbstractNode[] childNodes;

        public InnerNode(Vec3i position, int sizes) {
            super(position, sizes);

            childNodes = (sizes == 2) ? new LeafNode[8] : new InnerNode[8];
        }

        @Override
        boolean setBlock(Vec3i _position, Block _block) {
            int half = sizes / 2;
            Vec3i innerPosition = OctreeUtils.toInnerCoordinates(_position, half);
            Vec3i arrayCoordinates = OctreeUtils.toArrayCoordinates(_position, half);
            int arrayIndex = CoordinatesUtils.convert3dTo1d(arrayCoordinates, 2, 2);

            if (childNodes[arrayIndex] == null) {
                Vec3i newPos = position.plus(arrayCoordinates.multiplication(half));
                childNodes[arrayIndex] = (half == 1) ? new LeafNode(newPos, null) : new InnerNode(newPos, half);
            }

            return childNodes[arrayIndex].setBlock(innerPosition, _block);
        }

        @Override
        Block getBlock(Vec3i _position) {
            if (_position.x < 0 || _position.x >= 16 ||
                _position.y < 0 || _position.y >= 16 ||
                _position.z < 0 || _position.z >= 16) {

                return BlockRegistry.getInstance().get((short) 0);
            }
            int half = sizes / 2;
            Vec3i innerPosition = OctreeUtils.toInnerCoordinates(_position, half);
            Vec3i arrayCoordinates = OctreeUtils.toArrayCoordinates(_position, half);
            int arrayIndex = CoordinatesUtils.convert3dTo1d(arrayCoordinates, 2, 2);

            if (childNodes[arrayIndex] == null) {
                return BlockRegistry.getInstance().get((short) 0);
            }

            return childNodes[arrayIndex].getBlock(innerPosition);
        }

        @Override
        boolean deleteBlock(Vec3i _position) {
            int half = sizes / 2;
            Vec3i innerPosition = OctreeUtils.toInnerCoordinates(_position, half);
            Vec3i arrayCoordinates = OctreeUtils.toArrayCoordinates(_position, half);
            int arrayIndex = CoordinatesUtils.convert3dTo1d(arrayCoordinates, 2, 2);

            if (childNodes[arrayIndex] == null) {
                return false;
            }

            if (childNodes[arrayIndex].deleteBlock(innerPosition)) {
                if (childNodes[arrayIndex].getBlocksInsideCount() == 0) {
                    childNodes[arrayIndex] = null;
                }
                return true;
            }

            return false;
        }

        @Override
        int getBlocksInsideCount() {
            int count = 0;

            for (AbstractNode node : childNodes) {
                if (node != null) {
                    count += node.getBlocksInsideCount();
                }
            }
        }

        @Override
        void draw() {
            for (AbstractNode node : childNodes) {
                if (node != null) {
                    node.draw();
                }
            }
        }
    }

    private class LeafNode extends AbstractNode {
        Block data;

        public LeafNode(Vec3i position, Block data) {
            super(position, 0);
            this.data = data;
        }

        @Override
        boolean setBlock(Vec3i _position, Block _block) {
            if (data == null || !data.equals(_block)) {
                data = _block;
                return true;
            }

            return false;
        }

        @Override
        Block getBlock(Vec3i _position) {
            return data;
        }

        @Override
        boolean deleteBlock(Vec3i _position) {
            if (data != null) {
                data = null;
                return true;
            }

            return false;
        }

        @Override
        int getBlocksInsideCount() {
            return (data == null) ? 0 : 1;
        }

        @Override
        void draw() {
            Mat4 result = Mat4.computeTranslation(position.toVec3());
            glUniformMatrix4fv(0, false, result.toFloatArray());

            Mesh mesh;

            // todo delete this test shit
            if (Octree.this.getBlock(position.plus(Directions.bottom)).getId() == 0) {
                mesh = new Mesh(data.getModel().getBottomSide());
                mesh.draw();
                mesh.destroy();
            }

            if (Octree.this.getBlock(position.plus(Directions.top)).getId() == 0) {
                mesh = new Mesh(data.getModel().getTopSide());
                mesh.draw();
                mesh.destroy();
            }

            if (Octree.this.getBlock(position.plus(Directions.west)).getId() == 0) {
                mesh = new Mesh(data.getModel().getWestSide());
                mesh.draw();
                mesh.destroy();
            }

            if (Octree.this.getBlock(position.plus(Directions.east)).getId() == 0) {
                mesh = new Mesh(data.getModel().getEastSide());
                mesh.draw();
                mesh.destroy();
            }

            if (Octree.this.getBlock(position.plus(Directions.south)).getId() == 0) {
                mesh = new Mesh(data.getModel().getSouthSide());
                mesh.draw();
                mesh.destroy();
            }

            if (Octree.this.getBlock(position.plus(Directions.north)).getId() == 0) {
                mesh = new Mesh(data.getModel().getNorthSide());
                mesh.draw();
                mesh.destroy();
            }
        }
    }

    private static class OctreeUtils
    {
        static Vec3i toArrayCoordinates(Vec3i _coordinates, int _halfSizes)
        {
            // При верно заданных координатах (все от 0 до halfSizes * 2) компоненты принимают значения от 0 до 1
            int x = _coordinates.x / _halfSizes;
            int y = _coordinates.y / _halfSizes;
            int z = _coordinates.z / _halfSizes;

            return new Vec3i(x, y, z);
        }

        static Vec3i toInnerCoordinates(Vec3i _coordinates, int _halfSizes)
        {
            int x = _coordinates.x % _halfSizes;
            int y = _coordinates.y % _halfSizes;
            int z = _coordinates.z % _halfSizes;

            return new Vec3i(x, y, z);
        }
    }
}
